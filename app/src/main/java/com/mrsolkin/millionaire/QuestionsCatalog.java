package com.mrsolkin.millionaire;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuestionsCatalog {
    private static final String TAG = "QuestionsCatalog";
    private static final String QUESTIONS_FILENAME = "questions.json";

    private AssetManager mAssets;
    private List<Question> mQuestion;

    public QuestionsCatalog(Context context) {
        mAssets = context.getAssets();
    }

    public List<Question> getQuestion() {
        Collections.shuffle(mQuestion);
        return mQuestion;
    }

    public void loadQuestions() {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(mAssets.open(QUESTIONS_FILENAME)));
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                stringBuilder.append(buffer);
            }
        } catch (IOException e) {
            Log.e(TAG, "Не удалось загрузить список вопросов", e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (stringBuilder.length() > 0) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type listQuestion = new TypeToken<ArrayList<Question>>(){}.getType();

            try {
                mQuestion = gson.fromJson(stringBuilder.toString(), listQuestion);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
