package com.egormoroz.schooly.ui.chat.def;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.egormoroz.schooly.ui.chat.DemoMessagesActivity;
import com.egormoroz.schooly.ui.chat.fixtures.MessagesFixtures;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


import com.egormoroz.schooly.R;


public abstract class MessageActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageInput.TypingListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, MessageActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_messages);

        this.messagesList = findViewById(R.id.messagesList);
        initAdapter();

        RelativeLayout input = findViewById(R.id.input);
        ImageView sendfile=findViewById(R.id.sendimage);
        ImageView sendvoice=findViewById(R.id.voicemessage);
        ImageView sendmessage=findViewById(R.id.sendmessage);
        EditText editmessage=findViewById(R.id.editmessage);
        if(editmessage.isInEditMode()){
            sendmessage.setVisibility(View.VISIBLE);
            sendfile.setVisibility(View.GONE);
            sendvoice.setVisibility(View.GONE);
        }else {
            sendfile.setVisibility(View.VISIBLE);
            sendvoice.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onSubmit(CharSequence input) {
        super.messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(input.toString()), true);
        return true;
    }

    @Override
    public void onAddAttachments() {
        super.messagesAdapter.addToStart(
                MessagesFixtures.getImageMessage(), true);
    }

    private void initAdapter() {
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
//        super.messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
//                (view, message) -> AppUtils.showToast(MessageActivity.this,
//                        message.getUser().getName() + " avatar click",
//                        false));
        this.messagesList.setAdapter(super.messagesAdapter);
    }

    @Override
    public void onStartTyping() {
        Log.v("Typing listener", getString(R.string.start_typing_status));
    }

    @Override
    public void onStopTyping() {
        Log.v("Typing listener", getString(R.string.stop_typing_status));
    }
}
