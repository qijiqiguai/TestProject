package code.qijiqiguai.java.agent;

import com.sun.tools.attach.VirtualMachine;

/**
 * @author wangqi
 */
public class AttachAgent {

    public static void main(String[] args) throws Exception{
        VirtualMachine vm = VirtualMachine.attach("51716");
        vm.loadAgent("/Users/wangqi/Git/leark/TestProject/FrameDemo/target/FrameDemo-1.0-SNAPSHOT-jar-with-dependencies.jar");
    }

}
