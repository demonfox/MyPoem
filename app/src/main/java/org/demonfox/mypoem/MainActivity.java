package org.demonfox.mypoem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    enum Answer { YES, NO, ERROR};
    private enum EditState { EDITING, NOTEDITING };

    private static Answer choice;
    private EditState editState = EditState.NOTEDITING;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("This is an alert with no consequence");
            dlgAlert.setTitle("App Title");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }

    public void refreshPoem(View view) {
        if (editState == EditState.EDITING) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("您正处在编辑状态中，确认要放弃未保存的文字吗？");
            dlgAlert.setTitle("注意");
            dlgAlert.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // call your code here
                    choice = Answer.YES;
                    toggleButtonText(true);

                    displayRandomPoem();
                }
            });
            dlgAlert.setNegativeButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // call your code here
                    choice = Answer.NO;
                }
            });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        } else {
            displayRandomPoem();
        }
    }

    private void displayRandomPoem() {
        //Poem newPoem = getRandomPoem();

        EditText poemEditText = (EditText)findViewById(R.id.poemEditText);
        //poemEditText.setText(newPoem.getFullText());

        EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
        //titleEditText.setText(newPoem.getTitle());

        EditText dynastyEditText = (EditText)findViewById(R.id.dynastyEditText);
        //dynastyEditText.setText(newPoem.getDynasty());

        EditText authorEditText = (EditText)findViewById(R.id.authorEditText);
        //authorEditText.setText(newPoem.getAuthor());

        PoemQuery query = new PoemQuery();
        compositeDisposable.add(
                query.updatePoemText(poemEditText, titleEditText, dynastyEditText, authorEditText));
    }

//    private Poem getRandomPoem() {
//
//
//        //result.setFullText(selectPoemFromAzureTable());
//        return result;
//    }

    public void addPoem(View view) {
        toggleButtonText(true);
    }

    public void updatePoem(View view) {
        toggleButtonText(false);
    }

    private String selectPoemFromAzureTable() {
        try {
            PoemTableQuery p = new PoemTableQuery();
            p.start();
            p.join();
            return p.getPoemText();
        } catch (Exception e) {
            return e.toString();
        }
    }

    private void toggleButtonText(boolean clearText) {
        Button addButton = (Button)findViewById(R.id.addButton);
        Button updateButton = (Button)findViewById(R.id.updateButton);
        if (editState == EditState.NOTEDITING) {
            addButton.setText("保存");
            updateButton.setText("取消");

            EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
            if (clearText)
                titleEditText.setText("");
            titleEditText.setCursorVisible(true);
            titleEditText.setFocusable(true);
            titleEditText.setFocusableInTouchMode(true);
            titleEditText.setBackgroundColor(Color.argb(128, 180, 240, 220));
            //titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            titleEditText.requestFocus();

            EditText authorEditText = (EditText)findViewById(R.id.authorEditText);
            if (clearText)
                authorEditText.setText("");
            authorEditText.setCursorVisible(true);
            authorEditText.setFocusable(true);
            authorEditText.setFocusableInTouchMode(true);
            authorEditText.setBackgroundColor(Color.argb(128, 180, 240, 220));

            EditText dynastyEditText = (EditText)findViewById(R.id.dynastyEditText);
            if (clearText)
                dynastyEditText.setText("");
            dynastyEditText.setCursorVisible(true);
            dynastyEditText.setFocusable(true);
            dynastyEditText.setFocusableInTouchMode(true);
            dynastyEditText.setBackgroundColor(Color.argb(128, 180, 240, 220));

            EditText poemEditText = (EditText)findViewById(R.id.poemEditText);
            if (clearText)
                poemEditText.setText("");
            poemEditText.setCursorVisible(true);
            poemEditText.setFocusable(true);
            poemEditText.setFocusableInTouchMode(true);
            poemEditText.setBackgroundColor(Color.argb(128, 180, 240, 220));

            editState = EditState.EDITING;
        } else if (editState == EditState.EDITING) {
            addButton.setText("新增");
            updateButton.setText("更新");

            EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
            titleEditText.setCursorVisible(false);
            titleEditText.setFocusable(false);
            titleEditText.setFocusableInTouchMode(false);
            titleEditText.setBackgroundColor(Color.WHITE);
            //titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            titleEditText.requestFocus();

            EditText authorEditText = (EditText)findViewById(R.id.authorEditText);
            authorEditText.setCursorVisible(false);
            authorEditText.setFocusable(false);
            authorEditText.setFocusableInTouchMode(false);
            authorEditText.setBackgroundColor(Color.WHITE);

            EditText dynastyEditText = (EditText)findViewById(R.id.dynastyEditText);
            dynastyEditText.setCursorVisible(false);
            dynastyEditText.setFocusable(false);
            dynastyEditText.setFocusableInTouchMode(false);
            dynastyEditText.setBackgroundColor(Color.WHITE);

            EditText poemEditText = (EditText)findViewById(R.id.poemEditText);
            poemEditText.setCursorVisible(false);
            poemEditText.setFocusable(false);
            poemEditText.setFocusableInTouchMode(false);
            poemEditText.setBackgroundColor(Color.WHITE);

            editState = EditState.NOTEDITING;
        }
    }
}
