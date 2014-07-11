package com.mzhou.merchant.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import java.util.ArrayList;

import android.util.Log;

/**
 * 
 */

public class CustomTextView extends TextView {
	private String TAG = "BaikeTextView";
	public Context mContext = null;
	public Paint mPaint = null;
	public int mTextHeight = 1920;
	// public int mTextHeight = 1080;
	public int mBaikeTextHeight = 0;
	public int mTextWidth = 1920;
	public String mText = "";
	public float mLineSpace = 15;
	public int mOffset = -2;

	public float mTextSize = 0;

	public int mTextColor = 0xffbbbbbb;

	public int mFontHeight = 0;

	public int mPaddingLeft = 0;

	public int mPaddingRight = 0;

	public CustomTextView(Context context, AttributeSet set) {

		super(context, set);

		this.mContext = context;

		mPaint = new Paint();

		mPaint.setAntiAlias(true);

	}

	@Override
	protected void onDraw(Canvas canvas) {

		mTextWidth = this.getWidth();

		setmTextHeight(this.getHeight());

		mText = this.getText().toString().trim();

		mText = getTextString(mContext, mText);

		if (mText == null || mText.equals("") == true) {

			return;

		}

		Log.i(TAG, "mTextStr: " + mText + "");

		mTextSize = this.getTextSize();

		mFontHeight = (int) mTextSize;

		mPaddingLeft = this.getPaddingLeft();

		mPaddingRight = this.getPaddingRight();

		mTextColor = this.getCurrentTextColor();

		Log.i(TAG, "mTextSize: " + mTextSize + "");

		Log.i(TAG, "mFontHeight: " + mFontHeight + "");

		Log.i(TAG, "mPaddingLeft: " + mPaddingLeft + "");

		Log.i(TAG, "mPaddingRight: " + mPaddingRight + "");

		mPaint.setTextSize(mTextSize);

		mPaint.setColor(mTextColor);

		ArrayList<LinePar> tempLineArray = getLineParList(mText);

		drawText(tempLineArray, mText, canvas);

	}

	/*
	 * 
	 * Obtain the information of each row
	 */

	@SuppressLint("NewApi")
	public ArrayList<LinePar> getLineParList(String mTextStr) {

		if (mTextStr == null || mTextStr.equals("") == true) {

			return null;

		}

		int tempStart = 0;

		int tempLineWidth = 0;

		int tempLineCount = 0;

		ArrayList<LinePar> tempLineArray = new ArrayList<LinePar>();

		for (int i = 0; i < mTextStr.length(); i++) {

			char ch = mTextStr.charAt(i);

			String str = String.valueOf(ch);

			float strWidth = 0;

			if (str != null && str.trim().equals("") == false) {

				strWidth = BaikeConstant.getWidthofString(str, mPaint);

			}

			/*
			 * 
			 */

			if (ch == '\n' && tempStart != i) {

				tempLineCount++;

				addLinePar(tempStart, i, tempLineCount, 0, tempLineArray);

				if (i == (mTextStr.length() - 1)) {

					break;

				} else {

					tempStart = i + 1;

					tempLineWidth = 0;

				}

				continue;

			} else {

				tempLineWidth += Math.ceil(strWidth);

				if (tempLineWidth >= mTextWidth - mPaddingRight) {

					tempLineCount++;

					/*
					 * 
					 */

					if (BaikeConstant.isLeftPunctuation(ch) == true) {

						Log.i(TAG, "i: " + i + "");

						Log.i(TAG,

						"the char is the left half of the punctuation");

						Log.i(TAG, "str: " + str + " ");

						/*
						 * 
						 * if the char is the left half of the punctuation. Go
						 * 
						 * into the next line of the current character
						 */

						i--;

						float tempWordSpaceOffset = (float) (tempLineWidth

						- Math.ceil(strWidth) - mTextWidth)

						/ (float) (i - tempStart);

						addLinePar(tempStart, i, tempLineCount,

						tempWordSpaceOffset, tempLineArray);

					} else if (BaikeConstant.isRightPunctuation(ch) == true) {

						Log.i(TAG,

						"the char is the right half of the punctuation");

						Log.i(TAG, "str: " + str + " ");

						if (i == (mTextStr.length() - 1)) {

							addLinePar(tempStart, i, tempLineCount, 0,

							tempLineArray);

							break;

						} else {

							char nextChar = mTextStr.charAt(i + 1);

							if ((BaikeConstant.isHalfPunctuation(nextChar) == true || BaikeConstant

							.isPunctuation(nextChar) == true)

							&& BaikeConstant

							.isLeftPunctuation(nextChar) == false) {

								String nextStr = String.valueOf(nextChar);

								float nextStrWidth = 0;

								if (nextStr != null

								&& nextStr.trim().equals("") == false) {

									nextStrWidth = BaikeConstant

									.getWidthofString(nextStr, mPaint);

								}

								i++;

								float tempWordSpaceOffset = (float) (tempLineWidth

								+ Math.ceil(nextStrWidth) - mTextWidth)

								/ (float) (i - tempStart);

								addLinePar(tempStart, i, tempLineCount,

								tempWordSpaceOffset, tempLineArray);

							} else {

								float tempWordSpaceOffset = (float) (tempLineWidth - mTextWidth)

								/ (float) (i - tempStart);

								addLinePar(tempStart, i, tempLineCount,

								tempWordSpaceOffset, tempLineArray);

							}

						}

					} else {

						/*
						 * 
						 * if the char is not the left And Right half of the
						 * 
						 * punctuation.
						 */

						if (BaikeConstant.isHalfPunctuation(ch) == true

						|| BaikeConstant.isPunctuation(ch) == true) {

							/*
							 * 
							 * If the current character is a punctuation mark,
							 * 
							 * on the end of the Bank
							 */

							float tempWordSpaceOffset = (float) (tempLineWidth - mTextWidth)

							/ (float) (i - tempStart);

							addLinePar(tempStart, i, tempLineCount,

							tempWordSpaceOffset, tempLineArray);

						} else {

							/*
							 * 
							 * If the current character is not a punctuation
							 */

							if (i >= 1) {

								char preChar = mTextStr.charAt(i - 1);

								if (BaikeConstant.isLeftPunctuation(preChar) == true) {

									String preStr = String.valueOf(preChar);

									float preStrWidth = 0;

									if (preStr != null

									&& preStr.trim().equals("") == false) {

										preStrWidth = BaikeConstant

										.getWidthofString(preStr,

										mPaint);

									}

									Log.i(TAG,

									"the char is the left half of the punctuation");

									Log.i(TAG, "preChar: " + preChar + " ");

									i = i - 2;

									float tempWordSpaceOffset = (float) (tempLineWidth

									- Math.ceil(strWidth)

									- Math.ceil(preStrWidth) - mTextWidth)

									/ (float) (i - tempStart);

									addLinePar(tempStart, i, tempLineCount,

									tempWordSpaceOffset, tempLineArray);

								} else {

									i--;

									float tempWordSpaceOffset = (float) (tempLineWidth

									- Math.ceil(strWidth) - mTextWidth)

									/ (float) (i - tempStart);

									addLinePar(tempStart, i, tempLineCount,

									tempWordSpaceOffset, tempLineArray);

								}

							}

						}

					}

					if (i == (mTextStr.length() - 1)) {

						break;

					} else {

						tempStart = i + 1;

						tempLineWidth = 0;

					}

					continue;

				} else {

					if (i == (mTextStr.length() - 1)) {

						tempLineCount++;

						addLinePar(tempStart, i, tempLineCount, 0,

						tempLineArray);

						break;

					}

					continue;

				}

			}

		}

		return tempLineArray;

	}

	public void addLinePar(int start, int end, int lineCount,

	float wordSpaceOffset, ArrayList<LinePar> lineList) {

		if (lineList != null) {

			LinePar linePar = new LinePar();

			linePar.setLineCount(lineCount);

			linePar.setStart(start);

			linePar.setEnd(end);

			linePar.setWordSpaceOffset(wordSpaceOffset);

			lineList.add(linePar);

		}

	}

	public void drawText(ArrayList<LinePar> tempLineArray, String mTextStr,

	Canvas canvas) {

		if (tempLineArray == null || canvas == null || mTextStr == null

		|| mTextStr.equals("") == true) {

			return;

		}

		for (int lineNum = 0; lineNum < tempLineArray.size(); lineNum++) {

			LinePar linePar = tempLineArray.get(lineNum);

			int start = linePar.getStart();

			int end = linePar.getEnd();

			float width = linePar.getWordSpaceOffset();

			int lineCount = linePar.getLineCount();

			if (lineNum > 0 && lineNum == tempLineArray.size() - 1) {

				mBaikeTextHeight = (int) (lineCount * (mLineSpace + mTextSize));

			}

			if (start > end || end > mTextStr.length() - 1) {

				continue;

			}

			float lineWidth = 0;

			for (int strNum = start; strNum <= end; strNum++) {

				char ch = mTextStr.charAt(strNum);

				String str = String.valueOf(ch);

				if (str == null || str.equals("") == true) {

					continue;

				}

				if (ch == '\n') {

					str = "";

				}

				if (strNum > end) {

					break;

				}

				if (strNum >= start && strNum <= end && lineCount >= 1) {

					canvas.drawText(str, mPaddingLeft + lineWidth, lineCount

					* mFontHeight - mOffset + (lineCount - 1)

					* mLineSpace, mPaint);

					lineWidth += BaikeConstant.getWidthofString(str, mPaint);

					lineWidth = lineWidth - width;

				}

			}

		}

	}

	public int getBaikeTextHeight() {

		return mBaikeTextHeight;

	}

	public String getTextString(Context mContext, String mText) {

		if (mContext != null && mText != null && mText.equals("") == false) {

			return BaikeConstant.replaceTABToSpace(mText);

		}

		return "";

	}

	public void setmTextHeight(int mTextHeight) {

		this.mTextHeight = mTextHeight;

	}

	public int getmTextHeight() {

		return mTextHeight;

	}

	public class LinePar {

		private int mStart;

		private int mEnd;

		private int mLineCount;

		private float mWordSpaceOffset;

		public void setStart(int mStart) {

			this.mStart = mStart;

		}

		public void setEnd(int mEnd) {

			this.mEnd = mEnd;

		}

		public void setLineCount(int count) {

			this.mLineCount = count;

		}

		public void setWordSpaceOffset(float mWordSpaceOffset) {

			this.mWordSpaceOffset = mWordSpaceOffset;

		}

		public int getStart() {

			return mStart;

		}

		public int getEnd() {

			return mEnd;

		}

		public int getLineCount() {

			return mLineCount;

		}

		public float getWordSpaceOffset() {

			return mWordSpaceOffset;

		}

	}

}
