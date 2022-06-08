# coding=gbk
import cv2
import sys
import numpy
from pyzbar import pyzbar as pyzbar
import easyocr
import numpy as np

try:
    # filetype, file = "1", r"C:\Users\lenovo\Desktop\demo\.\src\main\resources\static\pics\Screenshot_20220527_114538.jpg"
    filetype, file = sys.argv[1], sys.argv[2]
    re = "0"
    # jiankangma
    if filetype == "1":
        file = cv2.imdecode(np.fromfile(file,dtype=np.uint8),-1)
        barcodes = pyzbar.decode(cv2.cvtColor(file, cv2.COLOR_BGR2GRAY))
        img_dst = None
        for barcode in barcodes:
            # 提取二维码的边界框的位置
            # 画出图像中条形码的边界框
            (x, y, w, h) = barcode.rect
            img_dst = file[y-5:y+h+5, x-5:x+w+5]
        # print(type(img_dst))
        if img_dst and type(img_dst) != None:
            img_dst = numpy.resize(img_dst, (100, 100, 3))
            color = numpy.sum(numpy.sum(img_dst, axis=1), axis=0)
            r, g, b = color[0], color[1], color[2]
            if r <= g-100000:
                re = "1"
            else:
                re = "0"
    # xingchnegma
    elif filetype == "2":
        ocr = easyocr.Reader(['ch_sim'],gpu=False)
        res = ocr.readtext(file,paragraph="False")
        for r in res:
            if "通信大数据行程卡" in r[-1]:
                re = 1
            if ("*" in r[-1]) and ("风险" in r[-1]):
                re = 0
    # wenzishibie 
    else:
        ocr = easyocr.Reader(['ch_sim'], gpu=False)
        res = ocr.readtext(file,paragraph="False")
        for r in res:
            if filetype in r[-1]:
                re = 1
    print(re)
except Exception as e:
    print(e)
