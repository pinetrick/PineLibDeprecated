package com.pine.lib.func.debug.window.key;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;
import com.pine.lib.hardware.info.D;

/**
 * 消息框
 * 
 * <pre>
 * 
 * </pre>
 */
public class DebugInfoBox implements OnItemClickListener
{
	private static G g = new G(DebugInfoBox.class);
	private static Boolean isShowing = false;

	private TextView mainText;

	private Context context;
	private KeyDebugDialog dialog;
	private Adapter adapter = new Adapter();



	private DebugInfoBox(Context context)
	{
		this.context = context;
	}


	/**
	 * 初始化
	 * 
	 * <pre>
	 * 必须将基础activity继承QFActivity/QFFragmentActivity
	 * 才能用这个初始化 
	 * 否则 请传入上下文
	 * </pre>
	 * 
	 * @return
	 */
	public static DebugInfoBox i()
	{
		return new DebugInfoBox(A.a());
	}


	public static DebugInfoBox i(Context context)
	{
		return new DebugInfoBox(context);
	}


	// 清理前端窗口
	public void dismiss()
	{
		isShowing = false;

		if (dialog != null)
		{
			dialog.cancel();
			dialog = null;
		}
	}


	/**
	 * 通过这里显示提示框
	 * 
	 * @param message
	 * @param buttons
	 */
	public void show()
	{

		if (context == null)
		{
			g.e("消息框无法显示 - 上下文失效");
			return;
		}

		if (!isShowing)
		{
			dialog = new KeyDebugDialog(context, R.style.dialog);

			dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
			dialog.show();
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0)
				{
					isShowing = false;

				}
			});
			isShowing = true;

			KeyDebugDialog view = dialog.getView();

			mainText = (TextView) view.findViewById(R.id.infoText);

			mainText.setText(M.i().toString() + "\n" + D.i().toString());

		}

	}


	public static Boolean getIsShowing()
	{
		return isShowing;
	}


	public static void setIsShowing(Boolean isShowing)
	{
		DebugInfoBox.isShowing = isShowing;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{

	}

}
