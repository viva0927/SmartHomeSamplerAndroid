/*
 * MessageFragmentDialog
 * SmartHomeSamplerAndroid
 *
 * Copyright (c) 2015 LG Electronics.
 * Created by Oleksii Frolov on 30 Mar 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.connectsdk.smarthomesampler.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class MessageFragmentDialog extends DialogFragment {

    public static final String ARG_MESSAGE = "message";
    private static final java.lang.String ARG_TITLE = "title";

    public static MessageFragmentDialog newInstance(String title, String message) {
        MessageFragmentDialog fragment = new MessageFragmentDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        Bundle args = getArguments();
        return new AlertDialog.Builder(context)
                .setTitle(args.getString(ARG_TITLE))
                .setMessage(args.getString(ARG_MESSAGE))
                .setCancelable(true)
                .create();
    }
}
