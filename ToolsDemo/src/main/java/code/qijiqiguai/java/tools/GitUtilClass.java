package code.qijiqiguai.java.tools;


import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GitUtilClass {
    public static String localRepoPath = "/Users/wangqi/Downloads/tmp/testgit";
    public static String localRepoGitConfig = "/Users/wangqi/Downloads/tmp/testgit/.git";
    public static String remoteRepoURI = "git@github.com:qijiqiguai/TestProject.git";

    public static void main(String[] args) {
        status();
    }

    public static void status() {
        File RepoGitDir = new File(localRepoGitConfig);
        Repository repo = null;
        try {
//            JschConfigSessionFactory jschConfigSessionFactory = new JschConfigSessionFactory() {
//                @Override
//                protected void configure(OpenSshConfig.Host hc, Session session) {
//                    session.setConfig("StrictHostKeyChecking","no");
//                }
//            };
//            SshSessionFactory.setInstance(jschConfigSessionFactory);
//
//            Git.cloneRepository()
//                    .setURI(remoteRepoURI)
//                    .setDirectory(new File(localRepoPath))
//                    .call();

            repo = new FileRepository(RepoGitDir.getAbsolutePath());
            Git git = new Git(repo);

//            Status status = git.status().call();
//            System.out.println("Git Change: " + status.getChanged());
//            System.out.println("Git Modified: " + status.getModified());
//            System.out.println("Git UncommittedChanges: " + status.getUncommittedChanges());
//            System.out.println("Git Untracked: " + status.getUntracked());
//
//
//            Iterable<RevCommit> iterable1 = git.log().setMaxCount(10).call();
//            Iterator<RevCommit> iter = iterable1.iterator();
//            while (iter.hasNext()){
//                RevCommit commit=iter.next();
//                String email=commit.getAuthorIdent().getEmailAddress();
//                String name=commit.getAuthorIdent().getName();  //作者
//
//                String commitEmail=commit.getCommitterIdent().getEmailAddress();//提交者
//                String commitName=commit.getCommitterIdent().getName();
//
//                int time=commit.getCommitTime();
//
//                String fullMessage=commit.getFullMessage();
//                String shortMessage=commit.getShortMessage();  //返回message的firstLine
//
//                String commitID=commit.getName();  //这个应该就是提交的版本号
//
//                System.out.println("authorEmail:"+email);
//                System.out.println("authorName:"+name);
//                System.out.println("commitEmail:"+commitEmail);
//                System.out.println("commitName:"+commitName);
//                System.out.println("time:"+time);
//                System.out.println("fullMessage:"+fullMessage);
//                System.out.println("shortMessage:"+shortMessage);
//                System.out.println("commitID:"+commitID);
//            }

            Repository repository = git.getRepository();
            List<RevCommit> list=new ArrayList<RevCommit>();
            Iterable<RevCommit> iterableDiff = git.log().addPath("FrameDemo/pom.xml").setMaxCount(2).call();
            for(RevCommit revCommit : iterableDiff){
                list.add(revCommit);
            }
            if(list.size()==2){
                AbstractTreeIterator newCommit=getAbstractTreeIterator(list.get(0),repository);
                AbstractTreeIterator oldCommit=getAbstractTreeIterator(list.get(1),repository);
                List<DiffEntry> diff=git.diff().setOldTree(oldCommit).setNewTree(newCommit).call();
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                DiffFormatter diffFormatter=new DiffFormatter(outputStream);
                //设置比较器为忽略空白字符对比（Ignores all whitespace）
                diffFormatter.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
                diffFormatter.setRepository(repository); // 这里为什么还要设置它
                for(DiffEntry diffEntry:diff){
                    diffFormatter.format(diffEntry);
                    System.out.println(outputStream.toString("UTF-8"));
                    outputStream.reset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (repo != null) {
                repo.close();
            }
        }
    }

    public static AbstractTreeIterator getAbstractTreeIterator(RevCommit commit, Repository repository ){
        RevWalk revWalk=new RevWalk(repository);
        CanonicalTreeParser treeParser=null;
        try {
            RevTree revTree=revWalk.parseTree(commit.getTree().getId());
            treeParser=new CanonicalTreeParser();
            treeParser.reset(repository.newObjectReader(),revTree.getId());
            revWalk.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return treeParser;
    }

}
