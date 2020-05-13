package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void Start(View v) {
        EditText text;
        TextView AnswerText;
        int length = 4; //кількість "предків"
        int[] x = new int[length];
        int y;
        double mutation;

        text = (EditText) findViewById(R.id.x1);
        x[0] = Integer.parseInt(text.getText().toString());

        text = (EditText) findViewById(R.id.x2);
        x[1] = Integer.parseInt(text.getText().toString());

        text = (EditText) findViewById(R.id.x3);
        x[2] = Integer.parseInt(text.getText().toString());

        text = (EditText) findViewById(R.id.x4);
        x[3] = Integer.parseInt(text.getText().toString());

        text = (EditText) findViewById(R.id.y);
        y = Integer.parseInt(text.getText().toString());

        text = (EditText) findViewById(R.id.mutation);
        mutation = Double.parseDouble(text.getText().toString());

        if (mutation > 0 && mutation <= 1) {

            AnswerText = (TextView) findViewById(R.id.Answer);
            AnswerText.setMovementMethod(new ScrollingMovementMethod());
            AnswerText.setText("Початок програми:\n");

            int[][] mass = new int[length + 1][length];
            double[] mass_sum = new double[length + 1];
            for (int i = 0; i < length + 1; i++) {
                for (int ii = 0; ii < length; ii++) {
                    mass[i][ii] = (int) (Math.random() * y / 2);
                    AnswerText.setText(AnswerText.getText() + "" + x[ii] + "*" + mass[i][ii] + " ");
                    if (ii != length - 1)
                        AnswerText.setText(AnswerText.getText() + "+ ");
                }
                AnswerText.setText(AnswerText.getText() + "\n");
            }
            AnswerText.setText(AnswerText.getText() + "\n");

            double average_summ = 0;
            int answer_index = -1;
            do {
                double max = 0;
                int max_index = 0;
                double sum = 0, koef = 0;
                for (int i = 0; i < length + 1; i++) {
                    mass_sum[i] = 0;
                    for (int ii = 0; ii < length; ii++) {
                        mass_sum[i] += mass[i][ii] * x[ii];
                    }
                    mass_sum[i] = mass_sum[i] - y;
                    if (mass_sum[i] < 0)
                        mass_sum[i] *= -1;

                    sum += mass_sum[i];

                    if (mass_sum[i] > max) {
                        max = mass_sum[i];
                        max_index = i;
                    }

                    if (mass_sum[i] == 0)
                        answer_index = i;
                    else
                        koef += 1 / mass_sum[i];
                }

                if (answer_index == -1) {
                    for (int i = 0; i < length + 1; i++) {
                        mass_sum[i] = 1 / mass_sum[i] / koef;
                    }


                    double mutation_rand = Math.random();
                    int suma = 0;
                    if (mutation_rand < mutation) {
                        for (int i = 0; i < length; i++) {
                            mass[max_index][i] = (int) (Math.random() * y / 2);
                            suma += mass[max_index][i] * x[i];
                        }
                        if (suma - y == 0)
                            answer_index = max_index;
                    }

                    if (answer_index == -1) {
                        int[][] mass_copy = new int[length + 1][length];
                        for (int i = 0; i < length + 1; i++) {
                            int first_descendant = (int) (Math.random() * 4);
                            if (first_descendant <= mass_sum[0])
                                first_descendant = 0;
                            else if (first_descendant > mass_sum[0] && first_descendant <= mass_sum[0] + mass_sum[1])
                                first_descendant = 1;
                            else if (first_descendant > mass_sum[0] && first_descendant <= mass_sum[0] + mass_sum[1] + mass_sum[2])
                                first_descendant = 2;
                            else if (first_descendant > mass_sum[0] && first_descendant <= mass_sum[0] + mass_sum[1] + mass_sum[2] + mass_sum[3])
                                first_descendant = 3;
                            else if (first_descendant > mass_sum[0] && first_descendant <= mass_sum[0] + mass_sum[1] + mass_sum[2] + mass_sum[3] + mass_sum[4])
                                first_descendant = 4;

                            int second_descendant;
                            do {
                                second_descendant = (int) (Math.random() * 4);
                                if (second_descendant <= mass_sum[0])
                                    second_descendant = 0;
                                else if (second_descendant > mass_sum[0] && second_descendant <= mass_sum[0] + mass_sum[1])
                                    second_descendant = 1;
                                else if (second_descendant > mass_sum[0] && second_descendant <= mass_sum[0] + mass_sum[1] + mass_sum[2])
                                    second_descendant = 2;
                                else if (second_descendant > mass_sum[0] && second_descendant <= mass_sum[0] + mass_sum[1] + mass_sum[2] + mass_sum[3])
                                    second_descendant = 3;
                                else if (second_descendant > mass_sum[0] && second_descendant <= mass_sum[0] + mass_sum[1] + mass_sum[2] + mass_sum[3] + mass_sum[4])
                                    second_descendant = 4;
                            } while (second_descendant == first_descendant);

                            int e = (int) (Math.random() * length - 1);
                            for (int ii = 0; ii <= e; ii++)
                                mass_copy[i][ii] = mass[second_descendant][ii];
                            for (int ii = e + 1; ii < length; ii++)
                                mass_copy[i][ii] = mass[first_descendant][ii];
                        }
                        for (int i = 0; i < length + 1; i++)
                            for (int ii = 0; ii < length; ii++) {
                                mass[i][ii] = mass_copy[i][ii];
                            }
                    }
                }
            } while (answer_index == -1);

            AnswerText.setText(AnswerText.getText() + "Кінечний спадкоємець:\n");
            for (int i = 0; i < length; i++) {
                AnswerText.setText(AnswerText.getText() + "" + x[i] + "*" + mass[answer_index][i] + " ");
                if (i != length - 1)
                    AnswerText.setText(AnswerText.getText() + "+ ");
            }
            AnswerText.setText(AnswerText.getText() + "= " + y);
        }
    }
}
