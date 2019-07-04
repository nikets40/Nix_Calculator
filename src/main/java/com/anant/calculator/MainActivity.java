package com.anant.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.ArrayList;
import java.util.Arrays;

import me.anwarshahriar.calligrapher.Calligrapher;


public class MainActivity extends AppCompatActivity {
    private String num;
    private EditText input;
    private TextView result;
    private Float var1, var2;
    private String[] inputarray;
    private Button equal;
    private ImageButton bks;
    private String[] chars;
    private ArrayList al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        al = new ArrayList<String>();
        chars = new String[]{"+", "-", "×", "÷", "√", "%", "(", ")"};

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "airbnbcereallight.ttf", true);
        result = findViewById(R.id.all_equation);
        input = findViewById(R.id.input_text);
        equal = findViewById(R.id.equal);
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Double r = new DoubleEvaluator().evaluate(input.getText().toString());
                    if (r == Math.round(r))
                        result.setText((int) Math.round(r));
                    else
                        result.setText(String.format("%s", r));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                final Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                slideUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Update the text here
//                    input.setText(result.getText());
//                    result.setText("");

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


                Animation slideUpFaster = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_faster);
                slideUpFaster.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        // Update the text here

                        var2 = Float.parseFloat(result.getText().toString());
                        result.setText("");
                        input.setText(var2.toString());


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                input.startAnimation(slideUpFaster);
                result.startAnimation(slideUp);


            }
        });
        bks = findViewById(R.id.backspace);


        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceType")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String str = input.getText().toString();

                if (checkexpression(str)) {
                    try {
                        if (input.getText().toString().contains("×")) {
                            str = str.replaceAll("×", "*");
                            System.out.println(str);
                        }

                        str = format(str);


                        Double r = new DoubleEvaluator().evaluate(str);
                        result.setText(r.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else result.setText("");


            }

            @Override
            public void afterTextChanged(Editable s) {

//                if (checkexpression(input.getText().toString())) {
//                    System.out.println("true");
//                    try {
//                        Double r = new DoubleEvaluator().evaluate(input.getText().toString());
//                        result.setText(r.toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else result.setText("");
            }
        });

    }

    private String format(String str) {

        str = str.replaceAll("×", "*");
        str = str.replaceAll("÷", "/");

        String[] s;
        if (str.contains("%")) {
            s = str.split("[+\\-*/]");
            System.out.println("str:-----------------------" + Arrays.toString(s));
            int s2 = Integer.parseInt(s[s.length]);
            int s1 = Integer.parseInt(s[s.length - 1]);
            s[s.length] = String.valueOf(s1 * s2 / 100);
            for (int i = str.length(); i > 0; i--) {
                if (!al.contains(str.charAt(i))){
//                    str.replace(str.charAt(i),"");
                }

            }

        }

        return str;
    }


    public void calc(View view) {

        switch (view.getTag().toString()) {

            case "C":
                input.setText("0");
                break;
            case "()":
                if (input.getText().toString().contains("("))
                    input.append(")");
                else
                    input.append("(");
                break;

            default:
                if (input.getText().toString().equals("0"))
                    input.setText(view.getTag().toString());

                else
                    input.append(view.getTag().toString());


        }


    }

    public void bks(View view) {

        if (input.getText().length() == 1) {
            input.setText("0");
        } else {

            if (input.getText() != null && input.getText().length() > 0) {
                input.setText(input.getText().toString().substring(0, input.getText().length() - 1));
            }
        }
        if (input.getText().toString().equals(""))
            input.setText("0");
    }

    public boolean checkexpression(String expression) {


        al.addAll(Arrays.asList(chars));


        for (int i = 0; i < expression.length(); i++) {
            if (al.contains(String.valueOf(expression.charAt(i))))

                return true;
        }
        return false;
    }
}



