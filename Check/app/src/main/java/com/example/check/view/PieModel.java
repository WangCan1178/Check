package com.example.check.view;

public class PieModel {

        public float	startAngle;	// 开始绘制的角度

        public float	sweepAngle;	// 扫过的角度

        public int		color;		// 显示的颜色

        public float	percent;	// 所占百分比

        public boolean	selected;	// true为选中


        public PieModel(int color, float percent) {
            this.color = color;
            this.percent = percent;
        }

        public PieModel(int color, float percent, boolean selected) {
            this.color = color;
            this.percent = percent;
            this.selected = selected;
        }
}
