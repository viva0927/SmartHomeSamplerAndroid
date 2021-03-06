/*
 * SetupActivity
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

package com.connectsdk.smarthomesampler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;

import com.connectsdk.smarthomesampler.scene.SceneConfig;
import com.connectsdk.smarthomesampler.scene.SceneController;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SetupActivity extends ActionBarActivity {

    private static final int REQUEST_SETUP_SCENE = 1;
    public static final String EXTRA_FORCE = "extra_force";

    @InjectView(R.id.btnDone)
    Button btnDone;

    @InjectView(R.id.textViewScene1State)
    TextView textViewScene1Scene;

    @InjectView(R.id.textViewScene2State)
    TextView textViewScene2Scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean forceSetup = getIntent().getBooleanExtra(EXTRA_FORCE, false);
        if (!forceSetup) {
            openMainActivityIfConfigured();
        }

        setContentView(R.layout.activity_setup);
        ButterKnife.inject(this);
        btnDone.setEnabled(forceSetup);

        updateScenesState();
    }

    private void updateScenesState() {
        if (isFirstSceneConfigured()) {
            textViewScene1Scene.setText(R.string.configured);
            textViewScene1Scene.setTextColor(Color.rgb(0x0, 0x99, 0x0));
        } else {
            textViewScene1Scene.setText(R.string.not_configured);
            textViewScene1Scene.setTextColor(Color.rgb(0x99, 0x00, 0x0));
        }

        if (isSecondSceneConfigured()) {
            textViewScene2Scene.setText(R.string.configured);
            textViewScene2Scene.setTextColor(Color.rgb(0x0, 0x99, 0x0));
        } else {
            textViewScene2Scene.setText(R.string.not_configured);
            textViewScene2Scene.setTextColor(Color.rgb(0x99, 0x00, 0x0));
        }
    }

    private void openMainActivityIfConfigured() {
        if (isSetUp()) {
            openMainActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETUP_SCENE) {
            btnDone.setEnabled(isSetUp());
            updateScenesState();
        }
    }

    private void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public boolean isSetUp() {
        return isFirstSceneConfigured() && isSecondSceneConfigured();
    }

    private boolean isFirstSceneConfigured() {
        return SceneConfig.loadFromPreferences(this, SceneController.SCENE_ID_FIRST).isConfigured();
    }

    private boolean isSecondSceneConfigured() {
        return SceneConfig.loadFromPreferences(this, SceneController.SCENE_ID_SECOND).isConfigured();
    }

    @OnClick(R.id.btnSceneOne)
    public void onConfigureFirstScene() {
        Intent intent = new Intent(this, SetupSceneActivity.class);
        intent.putExtra(SetupSceneActivity.EXTRA_SCENE_ID, SceneController.SCENE_ID_FIRST);
        startActivityForResult(intent, REQUEST_SETUP_SCENE);
    }

    @OnClick(R.id.btnSceneTwo)
    public void onConfigureSecondScene() {
        Intent intent = new Intent(this, SetupSceneActivity.class);
        intent.putExtra(SetupSceneActivity.EXTRA_SCENE_ID, SceneController.SCENE_ID_SECOND);
        startActivityForResult(intent, REQUEST_SETUP_SCENE);
    }

    @OnClick(R.id.btnDone)
    public void onDone() {
        openMainActivity();
    }
}
