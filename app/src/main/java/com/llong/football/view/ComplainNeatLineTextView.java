package com.llong.football.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llong.football.R;
import com.llong.football.db.bean.OptionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 适应子布局   逐个添加   规定一行三个    最左   最右   居中  三个  View
 *
 */
public class ComplainNeatLineTextView extends ViewGroup {

	private Context context;
	
	private OnChildViewClickListener listener;
	
	/**
	 * 子View中显示的内容数组
	 */
	private List<OptionInfo> infos;
	
	/**
	 * 用于存储每一子View是否选中的状态
	 */
	private List<String> states=new ArrayList<String>();

	private ChoiceType choiceType;

	/**
	 * 一行展示几个view，默认一个。
	 */
	private int lineNumber=1;
	
	/**
	 * 存储每一子View  
	 */
	private List<View> views=new ArrayList<>();
	
	public ComplainNeatLineTextView(Context context) {
		super(context);
	}

	public ComplainNeatLineTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ComplainNeatLineTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 数据的初始化
	 * @param context		上下文对象
	 * @param infos			子View中显示的内容数组
	 */
	public void init(Context context, List<OptionInfo> infos){
		removeAllViews();
		this.context=context;
		this.infos=infos;
		generateChildView();
	}

	public void setChoiceType(ChoiceType choiceType) {
		this.choiceType = choiceType;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		int childCount = getChildCount();
		int x = 0;
		int y = 0;
		int row = 0;
		int count=0;
		for (int index = 0; index < childCount; index++) {
			final View child = getChildAt(index);
			if (child.getVisibility() != View.GONE) {
				child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
				// 此处增加onlayout中的换行判断，用于计算所需的高度
				int width = child.getMeasuredWidth();
				int height = child.getMeasuredHeight();
				x =x + width;
				y = row * height + height;
				x = width;
				row++;
//				if(count==lineNumber-1){
//					x = width;
//					row++;
//					y = row * height + height;
//					count=0;
//				}else{
//					count++;
//				}
			}
		}
		// 设置容器所需的宽度和高度
		setMeasuredDimension(maxWidth, y);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int childCount = getChildCount();
		int maxWidth = r - l;
		int x = 0;
		int y = 0;
		int row = 0;
		int count=0;
		int line=0;
		int number=0;
		if(maxWidth>=480 && maxWidth<720){
			line=10;
		}else if(maxWidth>=720 && maxWidth<1080){
			line=15;
		}else{
			line=20;
		}
		for (int i = 0; i < childCount; i++) {
			final View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				int width = child.getMeasuredWidth();
				int height = child.getMeasuredHeight();
				y = row * height + height;
				row++;
//				if(count==0){
//					x=line;
//					if(i!=0){
//						row++;
//						y = row * height + height;
//					}
//					count++;
//				}else if(count==1){
//					x=(maxWidth/2)-(width/2);
//					count++;
//				}else if(count==2){
//					x = maxWidth-width-line;
//					count=0;
//				}

				child.layout(x, y - height, x+width, y);
			}
		}
	}
	
	/**
	 * 构建子View 
	 */
	private void generateChildView(){
		LayoutInflater infalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(int i=0;i<infos.size();i++){
			View view = infalter.inflate(R.layout.single_election, null);
			TextView textView=view.findViewById(R.id.select_value);
			OptionInfo info=infos.get(i);
			if(info==null){
				continue;
			}
			textView.setText(info.name);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for(int i=0;i<views.size();i++){
						ImageView type=views.get(i).findViewById(R.id.select_type);
						if(v==views.get(i)){
							if("true".equals(states.get(i))){
								states.set(i, "false");
								type.setImageResource(R.mipmap.select_no);
							}else if("false".equals(states.get(i))){
								states.set(i, "true");
								type.setImageResource(R.mipmap.select_off);
							}
						}else if(choiceType== ChoiceType.SINGLEELECTION){
							states.set(i, "false");
							type.setImageResource(R.mipmap.select_no);
						}
					}
					if(listener!=null){
						listener.onClick(getOptionValue(), getOptionId());
					}
				}
			});
			states.add("false");
			views.add(view);
			this.addView(view);
		}
	}

	/**
	 * 设置当子View被点击时回调的监听
	 * @param listener
	 */
	public void setOnChildViewClickLiner(OnChildViewClickListener listener){
		this.listener=listener;
	}
	
	/**
	 * 获取已选中的内容并拼接成字符串
	 * @return
	 */
	public String getOptionValue(){
		String value="";
		for(int i=0;i<states.size();i++){
			if("true".equals(states.get(i))){
				if(TextUtils.isEmpty(value)){
					value=infos.get(i).name;
				}else{
					value=value+","+infos.get(i);
				}
			}
		}
		if(!"".equals(value)){
			value=value.substring(1, value.length());
		}
		return value;
	}
	/**
	 * 获取已选中的内容并拼接成字符串
	 * @return
	 */
	public String getOptionId(){
		String value="";
		for(int i=0;i<states.size();i++){
			if("true".equals(states.get(i))){
				if(TextUtils.isEmpty(value)){
					value=infos.get(i).id;
				}else{
					value=value+","+infos.get(i);
				}
			}
		}
		if(!"".equals(value)){
			value=value.substring(1, value.length());
		}
		return value;
	}
	
	public interface OnChildViewClickListener{
		/**
		 * 当子View被点击时   获取已选中的内容
		 * @param value
		 */
		public void onClick(String value, String id);
	}

	public enum ChoiceType{
		/**
		 * 单选
		 */
		SINGLEELECTION,
		/**
		 * 多选
		 */
		MULTISELECTION
	}
}
