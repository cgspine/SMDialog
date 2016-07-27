package org.cgspine.smdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.cgspine.smdialog.lib.SMDialog;
import org.cgspine.smdialog.lib.SMDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    SMDialogBuilder builder1;
    SMDialogBuilder builder2;
    SMDialogBuilder builder3;
    SMDialogBuilder builder4;
    SMDialogBuilder builder5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setTitle("Demo")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder2.setTitle("Demo")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder3.setTitle("Demo")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder4.setTitle("Demo")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder5.setTitle("Demo")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });

        builder1 = new SMDialogBuilder(this) {
            @Override
            public View onBuildContent(SMDialog dialog) {
                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setPadding(20, 20, 20, 20);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for(int i=0;i<20;i++){
                    EditText editText = new EditText(MainActivity.this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setPadding(20, 20, 20, 20);
                    linearLayout.addView(editText);
                }

                return linearLayout;
            }

            @Override
            public int onGetScrollHeight() {
                return 1200;
            }
        };
        builder2 = new SMDialogBuilder(this) {
            @Override
            public View onBuildContent(SMDialog dialog) {
                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setPadding(20, 20, 20, 20);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for(int i=0;i<15;i++){
                    EditText editText = new EditText(MainActivity.this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setPadding(20, 20, 20, 20);
                    linearLayout.addView(editText);
                }

                return linearLayout;
            }

            @Override
            public int onGetScrollHeight() {
                return 900;
            }
        };

        builder3 = new SMDialogBuilder(this) {
            @Override
            public View onBuildContent(SMDialog dialog) {
                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setPadding(20, 20, 20, 20);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for(int i=0;i<15;i++){
                    EditText editText = new EditText(MainActivity.this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setPadding(20, 20, 20, 20);
                    linearLayout.addView(editText);
                }

                return linearLayout;
            }

            @Override
            public int onGetScrollHeight() {
                return 400;
            }
        };

        builder4 = new SMDialogBuilder(this) {
            @Override
            public View onBuildContent(SMDialog dialog) {
                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setPadding(20, 20, 20, 20);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for(int i=0;i<15;i++){
                    EditText editText = new EditText(MainActivity.this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setPadding(20, 20, 20, 20);
                    linearLayout.addView(editText);
                }

                return linearLayout;
            }

            @Override
            public int onGetScrollHeight() {
                return ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        };

        builder5 = new SMDialogBuilder(this) {
            @Override
            public View onBuildContent(SMDialog dialog) {
                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setPadding(20, 20, 20, 20);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for(int i=0;i<4;i++){
                    EditText editText = new EditText(MainActivity.this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setPadding(20, 20, 20, 20);
                    linearLayout.addView(editText);
                }

                return linearLayout;
            }

            @Override
            public int onGetScrollHeight() {
                return ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        };

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
