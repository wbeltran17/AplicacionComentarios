package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sqllite.entity.Comment;
import com.example.sqllite.persistent.OpenHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button createBtn, viewBtn, deleteBtn;
    private EditText nameTxt, commentTxt, viewNametxt, viewCommentTxt;

    private Spinner spinnerComment;
    private ArrayAdapter arrayAdapter;

    private ArrayList<Comment> commentList;
    private Comment comment;

    private OpenHelper openHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(R.string.create_commet_table);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTxt = findViewById(R.id.nameTxt);
        commentTxt = findViewById(R.id.commentTxt);
        viewNametxt = findViewById(R.id.editNameTxt);
        viewNametxt.setEnabled(false);
        viewCommentTxt = findViewById(R.id.editCommentTxt);
        viewCommentTxt.setEnabled(false);

        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(v -> createComment());
        viewBtn = findViewById(R.id.viewBtn);
        viewBtn.setOnClickListener(v -> setComment());
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(v -> deleteComment());

        String DB_NAME = getString(R.string.db_name);
        int DB_VERSION = Integer.parseInt(getString(R.string.db_version));
        openHelper = new OpenHelper(this, DB_NAME, null, DB_VERSION);

        spinnerComment = findViewById(R.id.commentSpinner);
        loadSpiner();
        spinnerComment.setOnItemSelectedListener(this);
    }

    public void loadSpiner() {
        commentList = openHelper.findAllComment();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, commentList);
        spinnerComment.setAdapter(arrayAdapter);
    }

    public void createComment() {
        comment = new Comment();
        comment.name = nameTxt.getText().toString();
        comment.comment = commentTxt.getText().toString();
        openHelper.saveComment(comment);
        loadSpiner();
        clearEditText(true);
    }

    public void clearEditText(boolean view) {
        if(view){
            nameTxt.setText("");
            commentTxt.setText("");
        }else {
            viewNametxt.setText("");
            viewCommentTxt.setText("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.commentSpinner) {
            if (commentList.size() > 0) {
                comment = commentList.get(position);

            }
        }
    }

    public void setComment() {
        if (this.comment != null) {
            viewNametxt.setText(this.comment.name);
            viewCommentTxt.setText(this.comment.comment);
        }
    }

    public void deleteComment() {
        if (this.comment != null) {
            clearEditText(false);
            openHelper.deleteComment(this.comment);
            loadSpiner();
            this.comment = null;
            System.gc();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}