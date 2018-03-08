package com.mandy.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;

/**
 * All rights Reserved, Designed By www.lagou.com
 * Title:  AnalyzerDoc
 * Package com.mandy.lucene
 * Description:    分析器测试
 * Date:   2018/3/8 15:46
 *
 * @author mandy
 * @version V1.0
 */
public class AnalyzerDoc {

    public static void main(String[] args) throws IOException {
        //初始化分析器
        Analyzer analyzer=new IKAnalyzer(true);

        /**
         * lucene自带中文分析器: 举例:我是中国人
         *  1.StamdardAnalyzer （lucene-analyzers-common中）标准分析器                            我   是   中   国   人
         *  2.CJKAnalyzer（lucene-analyzers-common中）二分法分析器                                我是   是中  中国  国人
         *  3.SmartChineseAnalyzer(lucene-analyzers-smartcn中)对中文支持较好，但扩展性差         我   是   中国  人
         * 第三方中文分析器：
         *  1.IKAnalyzer（useSmart:true）                                                        我   是   中国人
         *  2.IKAnalyzer(useSmart:false)                                                         我  是   中国人   中国    国人
         */
        //从分析器对象中获取分析内容:参数一:域名称可以为null或“” ，参数二:待分析字符串
        TokenStream tokenStream=analyzer.tokenStream("","我是中国人");
        //设置引用：关键词引用，偏移量引用等
        CharTermAttribute charTermAttribute=tokenStream.addAttribute(CharTermAttribute.class);
        OffsetAttribute offsetAttribute=tokenStream.addAttribute(OffsetAttribute.class);
        //指针重置
        tokenStream.reset();
        //遍历单词表
        while(tokenStream.incrementToken()){
            System.out.println("分词后，开始:\t"+offsetAttribute.startOffset());
            System.out.println("分词后，内容:\t"+charTermAttribute);
            System.out.println("分词后，结束:\t"+offsetAttribute.endOffset());
        }
        //关闭资源
        tokenStream.close();
    }
}
