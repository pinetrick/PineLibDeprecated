package com.pine.lib.windows.alter;

import java.util.ArrayList;
import java.util.List;

import com.pine.lib.func.debug.G;

/**
 * 提示框消息队列 保证同一时刻 只有一个消息框在显示
 */
public class MessageBoxQueue {
	private static G g = new G(MessageBoxQueue.class);
	private List<MessageBox> queue = new ArrayList<MessageBox>();

	public List<MessageBox> getQueue() {
		return queue;
	}

	public void setQueue(List<MessageBox> queue) {
		this.queue = queue;
	}

	public synchronized void showNext() {
		if (!MessageBox.getIsShowing()) {
			if (queue.size() > 0) {
				MessageBox mBox = queue.get(0);
				try {
					mBox.showNow(mBox.getMessage(), mBox.getButtons());
				} catch (Exception e) {
					g.d("Msgbox显示错误 - " + e.toString());
				}
				queue.remove(mBox);
			}
		}
	}
}
