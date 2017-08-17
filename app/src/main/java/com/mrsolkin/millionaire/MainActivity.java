package com.mrsolkin.millionaire;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private TextView mQuestion;
    private TextView mCorrectAnswerCount;

    private QuestionsCatalog mQuestions;
    private Question currentQuestion;
    private List<Question> questionList;
    private int pos = 0;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerA = (Button) findViewById(R.id.answerA);
        answerB = (Button) findViewById(R.id.answerB);
        answerC = (Button) findViewById(R.id.answerC);
        answerD = (Button) findViewById(R.id.answerD);
        mQuestion = (TextView) findViewById(R.id.question);
        mCorrectAnswerCount = (TextView) findViewById(R.id.correctAnswersCount);

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);

        mQuestions = new QuestionsCatalog(this);

        new LoadQuestionsTask().execute();
    }

    @Override
    public void onClick(View view) {
        String userAnswer = ((TextView) view).getText().toString();
        if (userAnswer.equals(currentQuestion.getCorrect())) {
            Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
            correctAnswers++;
        } else {
            Toast.makeText(this, "Неправильно!", Toast.LENGTH_SHORT).show();
            questionList = mQuestions.getQuestion();
            correctAnswers = 0;
        }

        mCorrectAnswerCount.setText("Правильных ответов: "+correctAnswers);
        getNextQuestion();
    }

    private void getNextQuestion() {

        if (pos < questionList.size()) {
            currentQuestion = questionList.get(pos);
            rebuild(currentQuestion);
        }
        pos++;
    }

    private class LoadQuestionsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mQuestions.loadQuestions();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            questionList = mQuestions.getQuestion();
            getNextQuestion();
        }
    }

    private void rebuild(Question q) {
        mQuestion.setText(q.getQuestion());
        answerA.setText(q.getAnswers().get(0));
        answerB.setText(q.getAnswers().get(1));
        answerC.setText(q.getAnswers().get(2));
        answerD.setText(q.getAnswers().get(3));
    }
}
