/*
 * 项目名称:ReductionAlgorithmTool
 * 文件名称:AbstractService.java
 * Date:2024/1/30 下午5:42
 * Author:王贝强
 */

package reductionalgorithm.GUI.service;


import reductionalgorithm.GUI.windows.AbstractWindow;

import java.awt.*;
import java.util.function.Function;

/**
* @Description: AbstractService是所有窗口业务层实现的顶层抽象。
 * 这个类只进行各项实际业务处理，不负责UI相关内容
* @Author: 王贝强
* @Date: 2024/1/30
*/
public abstract class AbstractService {

    private AbstractWindow<? extends AbstractService> window;
    private Function<String, Component> componentGetter;

    /**
     * 方便AbstractWindows进行服务配置，快速指定当前Service所属的窗口。
     * @param window 当前业务层所属窗口
     */
    public final void setWindow(AbstractWindow<? extends AbstractService> window, Function<String, Component> componentGetter) {
        this.window = window;
        this.componentGetter = componentGetter;
    }

    /**
     * 通过组件名称，快速得到对应组件对象
     * @param componentName 组件名称
     * @return 组件对象
     */
    @SuppressWarnings("unchecked")
    protected final <T extends Component> T getComponent(String componentName){
        return (T) this.componentGetter.apply(componentName);
    }

    /**
     * 获取当前业务实现的所属窗口
     * @return 窗口
     */
    protected final AbstractWindow<? extends AbstractService> getWindow(){
        return this.window;
    }
}
