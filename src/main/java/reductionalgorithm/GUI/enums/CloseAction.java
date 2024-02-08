/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:CloseAction.java
 * Date:2024/1/30 下午6:58
 * Author:王贝强
 */

package reductionalgorithm.GUI.enums;

import reductionalgorithm.GUI.windows.AbstractWindow;

import java.awt.*;
import java.util.function.Consumer;

/**
* @Description: 预定义的窗口关闭行为
* @Author: 王贝强
* @Date: 2024/1/30
*/
public enum CloseAction {
    DISPOSE(Window::dispose),    //调用窗口的dispose方法
    EXIT(window -> System.exit(0));   //调用窗口的退出方法

    private final Consumer<AbstractWindow<?>> action;//窗口关闭行为
    CloseAction(Consumer<AbstractWindow<?>> action){
        this.action = action;
    }

    public void doAction(AbstractWindow<?> window){
        action.accept(window);
    }
}
