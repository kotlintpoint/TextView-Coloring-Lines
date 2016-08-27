package colorline.sodhankit.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int COLOUR = 1;
    TextView mTextView;
    static String finalString=null;
    ColorPickerSeekBar colorPickerSeekBar;
    String selectedText="";
    int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView=(TextView)findViewById(R.id.tvData);

        mTextView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Remove the "select all" option
                menu.removeItem(android.R.id.selectAll);
                // Remove the "cut" option
                menu.removeItem(android.R.id.cut);
                // Remove the "copy all" option
                menu.removeItem(android.R.id.copy);

                menu.removeItem(android.R.id.shareText);



                return false;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Called when action mode is first created. The menu supplied
                // will be used to generate action buttons for the action mode

                // Here is an example MenuItem
                menu.add(0, COLOUR, 0, "Color").setIcon(R.mipmap.ic_launcher);

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Called when an action mode is about to be exited and
                // destroyed
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case COLOUR:
                        int min = 0;
                        int max = mTextView.getText().length();
                        if (mTextView.isFocused()) {
                            final int selStart = mTextView.getSelectionStart();
                            final int selEnd = mTextView.getSelectionEnd();

                            min = Math.max(0, Math.min(selStart, selEnd));
                            max = Math.max(0, Math.max(selStart, selEnd));
                        }
                        // Perform your definition lookup with the selected text
                        selectedText = mTextView.getText().toString().substring(min, max);

                        selectedTextColorChange(selectedText);
                        // Finish and close the ActionMode
                        mode.finish();
                        return true;
                    default:
                        break;
                }
                return false;
            }

        });

        colorPickerSeekBar=(ColorPickerSeekBar)findViewById(R.id.colorpicker);
        colorPickerSeekBar.setOnColorSeekbarChangeListener(new ColorPickerSeekBar.OnColorSeekBarChangeListener() {
            @Override
            public void onColorChanged(SeekBar seekBar, int color, boolean fromUser) {
                //view.setBackgroundColor(color);
                mColor=color;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void selectedTextColorChange(String selectedText) {

        String hexColor = String.format("#%06X", (0xFFFFFF & mColor));
        if(finalString==null)
        {
            finalString=mTextView.getText().toString();
        }
        String text=finalString;
        String htmlString="<font>"+text.substring(0,text.indexOf(selectedText))+"</font>";
        htmlString+="<font color="+hexColor+">"+selectedText+"</font>";
        htmlString+="<font>"+text.substring(text.indexOf(selectedText)+selectedText.length())+"</font>";
        finalString=htmlString;
        mTextView.setText(Html.fromHtml(htmlString));
    }

}
