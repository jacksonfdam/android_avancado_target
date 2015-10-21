package br.com.targettrust.exercicio04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class MainActivity extends AppCompatActivity {
 
    private Toolbar mToolbar;
    private CoordinatorLayout coordinatorLayout;
    private Button btnSimpleSnackbar, btnActionCallback, btnCustomView;
    private FloatingActionButton fab;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
 
        fab = (FloatingActionButton) findViewById(R.id.fab);
 
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
 
        setSupportActionBar(mToolbar);
 
        btnSimpleSnackbar = (Button) findViewById(R.id.btnSimpleSnackbar);
        btnActionCallback = (Button) findViewById(R.id.btnActionCallback);
        btnCustomView = (Button) findViewById(R.id.btnCustomSnackbar);
 
        btnSimpleSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Exercicio 04", Snackbar.LENGTH_LONG);
 
                snackbar.show();
            }
        });
 
        btnActionCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Usuário Excluido!", Snackbar.LENGTH_LONG)
                        .setAction("DESFAZER", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Usuário restaurado!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });
 
                snackbar.show();
            }
        });
 
        btnCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Sem conexão com a internet!", Snackbar.LENGTH_LONG)
                        .setAction("TENTAR", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
 
                snackbar.setActionTextColor(Color.RED);
 
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
 
                snackbar.show();
            }
        });
    }
}