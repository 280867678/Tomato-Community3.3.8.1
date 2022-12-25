package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer;
import com.p076mh.webappStart.util.ActivityManager;
import com.p076mh.webappStart.util.TimeUtils;
import java.io.File;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayingDetailActivity */
/* loaded from: classes3.dex */
public class PlayingDetailActivity extends AppCompatActivity implements CZMediaPlayer.PlayStateListener {
    private static final String TAG = "PlayingDetailActivity";
    private ImageView btn_play_controll;
    private SeekBar seekBar;
    private TextView tv_current_time;
    private TextView tv_total_time;

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
    public void onPrepare() {
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mediaPlayer) {
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
    public void onSeeking() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityManager.getInstance().addActivity(this);
        setContentView(R$layout.web_sdk_activity_playing_detail);
        initView();
        PlayerServiceMananger.getInstance().getMediaPlayer().addPlayStateListener(this);
    }

    private void initView() {
        final ImageView imageView = (ImageView) findViewById(R$id.img_cover);
        if (TextUtils.isEmpty(PlayerServiceMananger.getInstance().getMediaPlayer().getCoverImgUrl())) {
            Log.e(TAG, "initView: CoverImgUrl not setted");
        } else if (PlayerServiceMananger.getInstance().getMediaPlayer().getCoverImgUrl().startsWith("app")) {
            SelectionSpec.getInstance().imageEngine.load(this, imageView, PlayerServiceMananger.getInstance().getMediaPlayer().getWebViewFragment().realPath(PlayerServiceMananger.getInstance().getMediaPlayer().getCoverImgUrl()));
        } else {
            SelectionSpec.getInstance().imageEngine.download(WebApplication.getInstance().getApplication(), PlayerServiceMananger.getInstance().getMediaPlayer().getCoverImgUrl(), new CommonCallBack<File>() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayingDetailActivity.1
                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                public void onResult(File file) {
                    SelectionSpec.getInstance().imageEngine.load(PlayingDetailActivity.this, imageView, file);
                }

                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                public void onFailure(Exception exc) {
                    exc.printStackTrace();
                }
            });
        }
        ((TextView) findViewById(R$id.tv_title)).setText(PlayerServiceMananger.getInstance().getMediaPlayer().getTitle());
        this.btn_play_controll = (ImageView) findViewById(R$id.btn_play_controll);
        this.btn_play_controll.setImageResource(PlayerServiceMananger.getInstance().getMediaPlayer().isPlaying() ? R$mipmap.btn_pause : R$mipmap.btn_play);
        this.tv_current_time = (TextView) findViewById(R$id.tv_current_time);
        this.tv_total_time = (TextView) findViewById(R$id.tv_total_time);
        this.seekBar = (SeekBar) findViewById(R$id.seekBar);
        this.seekBar.setMax(100);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayingDetailActivity.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                PlayerServiceMananger.getInstance().getMediaPlayer().wx_seek((int) (((seekBar.getProgress() / 100.0f) * PlayerServiceMananger.getInstance().getMediaPlayer().getDuration()) / 1000.0f));
            }
        });
        this.btn_play_controll.setOnClickListener(new View.OnClickListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayingDetailActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlayerServiceMananger.getInstance().change();
            }
        });
        findViewById(R$id.btn_back).setOnClickListener(new View.OnClickListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayingDetailActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlayingDetailActivity.this.finish();
            }
        });
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.seekBar.setProgress(100);
        this.btn_play_controll.setImageResource(R$mipmap.btn_play);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
    public void onMpStart() {
        this.btn_play_controll.setImageResource(R$mipmap.btn_pause);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
    public void onMpStop() {
        this.btn_play_controll.setImageResource(R$mipmap.btn_play);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
    public void onMpPause() {
        this.btn_play_controll.setImageResource(R$mipmap.btn_play);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
    public void onPlaying(int i, int i2) {
        Logger.m4112i(TAG, "onPlaying,currentPosition = " + i + ",duration = " + i2);
        this.tv_current_time.setText(TimeUtils.calculatTimeHMS((long) i));
        this.tv_total_time.setText(TimeUtils.calculatTimeHMS((long) i2));
        this.seekBar.setProgress((int) ((((float) i) * 100.0f) / ((float) i2)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }
}
