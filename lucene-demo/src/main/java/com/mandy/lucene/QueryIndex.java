package com.mandy.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;

/**
 * All rights Reserved, Designed By www.lagou.com
 * Title:  QueryIndex
 * Package com.mandy.lucene
 * Description:    查询索引
 * Date:   2018/3/8 14:17
 *
 * @author mandy
 * @version V1.0
 */
public class QueryIndex {
    public static void main(String[] args) throws IOException {
        //创建索引库对象
        try(Directory directory = FSDirectory.open(new File("D:\\lucene\\directory"))){
            //创建读对象
            IndexReader indexReader= DirectoryReader.open(directory);
            IndexSearcher indexSearcher=new IndexSearcher(indexReader);
            //查询语句
            Query query=new TermQuery(new Term("content","tom"));
            //接收查询结果
            TopDocs topDocs=indexSearcher.search(query,10);
            System.out.println(topDocs.totalHits);//totalHits获取查询结果集大小
            for(ScoreDoc scoreDoc:topDocs.scoreDocs){
                int docId = scoreDoc.doc;
                float score = scoreDoc.score;
                int shardIndex = scoreDoc.shardIndex;
                //根据查询结果中的docId查询文档
                Document document=indexSearcher.doc(docId);
            }
            //关闭资源
            indexReader.close();
        }
    }
}
