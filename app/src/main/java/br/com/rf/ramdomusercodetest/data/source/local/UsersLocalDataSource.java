/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.rf.ramdomusercodetest.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.TreeSet;
import java.util.UUID;

import br.com.rf.ramdomusercodetest.data.source.UsersDataSource;
import br.com.rf.ramdomusercodetest.data.source.local.UsersPersistenceContract.UserEntry;
import br.com.rf.ramdomusercodetest.model.User;
import br.com.rf.ramdomusercodetest.model.UserWrapper;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class UsersLocalDataSource implements UsersDataSource {

    private static UsersLocalDataSource INSTANCE;

    private UsersDbHelper mDbHelper;

    // Prevent direct instantiation.
    private UsersLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new UsersDbHelper(context);
    }

    public static UsersLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UsersLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getUsers(@NonNull LoadUsersCallback callback) {
        TreeSet<User> users = new TreeSet<User>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                UserEntry.COLUMN_NAME_ENTRY_ID,
                UserEntry.COLUMN_NAME_JSON,

        };

        Cursor c = db.query(
                UserEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_ENTRY_ID));
                String json = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_JSON));

                UserWrapper userWrapper = new Gson().fromJson(json, UserWrapper.class);
                users.addAll(userWrapper.results);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (users.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onUsersLoaded(users);
        }

    }

    @Override
    public void saveUsers(@NonNull UserWrapper userWrapper) {
        checkNotNull(userWrapper);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_ENTRY_ID, UUID.randomUUID().toString());
        values.put(UserEntry.COLUMN_NAME_JSON, new Gson().toJson(userWrapper));

        db.insert(UserEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void saveUsers(@NonNull TreeSet<User> users) {
        checkNotNull(users);
        saveUsers(new UserWrapper(users));
    }

    @Override
    public void deleteAllUsers() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(UserEntry.TABLE_NAME, null, null);

        db.close();
    }

    @Override
    public void deleteUser(@NonNull User user, @NonNull TreeSet<User> allUsers) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        UserWrapper userWrapper = new UserWrapper(allUsers);

        db.delete(UserEntry.TABLE_NAME, null, null);

        db.close();

        saveUsers(userWrapper);

    }
}
