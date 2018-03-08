package com.mandy.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * All rights Reserved, Designed By www.lagou.com
 * Title:  CreateIndex
 * Package com.mandy.lucene
 * Description:    创建索引库
 * Date:   2018/3/8 14:00
 *
 * @author mandy
 * @version V1.0
 */
public class CreateIndex {
    public static void main(String[] args) throws IOException {
        // 定义索引库
        //Directory directory=new RAMDirectory();//RAMDirectory 存放在内存中
        Directory directory= FSDirectory.open(new File("D:\\lucene\\directory"));//FSDirectory 存放在文件系统中
        //定义分析器
        Analyzer analyzer=new StandardAnalyzer();//标准分析器
        //version代表lucene版本号
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(Version.LATEST,analyzer);
        IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig);
        //加载原始文档
        File file=new File("D:\\lucene\\data");
        for(File f:file.listFiles()){
            //获取文件名称，路径，大小，内容
            String fileName=f.getName();
            long fileSize= FileUtils.sizeOf(f);
            String fileContent=FileUtils.readFileToString(f);
            String filePath=f.getPath();
            //创建文档对象
            Document document=new Document();
            //创建域
            StringField nameField=new StringField("name",fileName, Field.Store.YES);
            LongField sizeField=new LongField("size",fileSize, Field.Store.YES);
            TextField contentField=new TextField("content",fileContent, Field.Store.NO);//分析，索引，但不需要存储
            StoredField pathField=new StoredField("path",filePath);//此域不分析，不索引，要保存
            //将域加入文档
            document.add(nameField);
            document.add(sizeField);
            document.add(contentField);
            document.add(pathField);
            //将文档写入索引库
            indexWriter.addDocument(document);
        }
        //释放资源
        indexWriter.close();
    }
}
