package com.pine.lib.media.playvoice;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.View;

import com.pine.lib.base.activity.A;

public class Mp3Player
{
	private static SoundPool soundPool;
	private static MediaPlayer mediaPlayer;






	public static void play(int resid)
	{
		mediaPlayer = MediaPlayer.create(A.c(), resid);
		// mediaPlayer.prepare();
		mediaPlayer.start();
	}


	public static void stop()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
		}

	}


	public static Boolean play(String url)
	{
		try
		{
			MediaPlayer mediaPlayer = new MediaPlayer();
			if (mediaPlayer.isPlaying())
			{
				mediaPlayer.reset();// 重置为初始状态
			}
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepare();// 缓冲
			mediaPlayer.start();// 开始或恢复播放
			mediaPlayer.pause();// 暂停播放
			mediaPlayer.start();// 恢复播放
			mediaPlayer.stop();// 停止播放
			mediaPlayer.release();// 释放资源

			return true;
		}
		catch (Exception e)
		{
			return false;
		}

	}


	public static void playUseless1(int res)
	{
		/**
		 * 将soundPool实例化，第一个参数为soundPool可以支持的声音数量，
		 * 这决定了Android为其开设多大的缓冲区，第二个参数为声音类型，
		 * 在这里标识为系统声音，除此之外还有AudioManager.STREAM_RING以及
		 * AudioManager.STREAM_MUSIC等，系统会根据不同的声音为其标志不同的
		 * 优先级和缓冲区，最后参数为声音品质，品质越高，声音效果越好，但耗费 更多的系统资源。
		 */
		soundPool = new SoundPool(10, AudioManager.STREAM_RING, 5);
		/**
		 * 系统为soundPool加载声音，第一个参数为上下文参数，第二个参数为声音的id， 一般我们将声音信息保存在res的raw文件夹下。
		 * 第三个参数为声音的优先级，当多个声音冲突而无法同时播放时，系统会优先播放优先级高的。
		 */
		soundPool.load(A.c(), res, 1);
		/**
		 * 第四行就是播放了，第一个参数为id，id即为放入到soundPool中的顺序，
		 * 比如现在collide.wav是第一个，因此它的id就是1。 第二个和第三个参数为左右声道的音量控制。
		 * 第四个参数为优先级，由于只有这一个声音，因此优先级在这里并不重要。 第五个参数为是否循环播放，0为不循环，-1为循环。
		 * 最后一个参数为播放比率，从0.5到2，一般为1，表示正常播放。
		 */
		soundPool.play(1, 100, 100, 0, 0, 1);
	}

}
