package reductionalgorithm.GUI.windows;

import reductionalgorithm.GUI.enums.CloseAction;
import reductionalgorithm.GUI.service.AbstractService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * AbstractWindow 是当前项目中，所有窗口的顶层抽象类，继承自JFrame类。
 * 考虑到窗口功能复杂，所以：
 * - 窗口内只需要编写组件部分，仅包括UI相关内容。
 * - 每个窗口都有一个对应的Service来对各项功能进行具体实现。
 * - 与工具类、管理类这些底层操作相关的，只能由Service来交互。
 * 因此，在编写窗口的时候需要分三层来写,例如：
 * - WelcomeWindow <-> InputService <-> Tools、Manager
 * - MainWindow <-> MainService <-> Tools、Manager
 */
public abstract class AbstractWindow<R extends AbstractService> extends JFrame {
    //窗口的默认关闭行为
    private CloseAction action = CloseAction.DISPOSE;
    //窗口的业务层实现
    protected R service;
    //当前窗口中所有的组件表，方便业务层快速获取对应组件
    private final Map<String, Component> componentMap = new HashMap<>();

    /**
     * 所有继承自此类实现的窗口，都需要添加以下几个参数，用于快速设定窗口必要属性
     * @param title 窗口标题
     * @param defaultSize 默认大小
     * @param resizeable 是否可以修改大小
     */
    protected AbstractWindow(String title, Dimension defaultSize, boolean resizeable, Class<R> clazz){
        //首先设定窗口的标题、默认大小、是否可修改大小等
        this.setTitle(title);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   //注意，设定大小不能超过屏幕尺寸
        this.setSize((int) Math.min(screenSize.getWidth(), defaultSize.getWidth()),
                (int) Math.min(screenSize.getHeight(), defaultSize.getHeight()));
        this.setResizable(resizeable);

        //接着计算窗口的中心位置，将窗口移动到屏幕中心
        Point screenCenter = calculateCenter(screenSize.getWidth(), screenSize.getHeight(),
                defaultSize.getWidth(), defaultSize.getHeight());
        this.setLocation(screenCenter);

        //将窗口设计成点击X号不直接关闭，由子类来决定是直接关闭还是做一些其他的事情
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(onClose()) {   //调用抽象方法，是否需要关闭窗口由子类的具体实现进行判断
                    AbstractWindow.this.closeWindow();
                }
            }
        });

        //最后设定窗口的业务层，直接反射创建并完成配置
        try {
            this.service = clazz.getConstructor().newInstance();
            this.service.setWindow(this, componentMap::get);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        //构造方法中对窗口的基本内容初始化完成
    }

    /**
     * 传入外部长宽与窗口（内部）长宽，计算中心位置，用于窗口居中位置计算
     * @param outerWidth 外部宽度（如屏幕宽度）
     * @param outerHeight 外部高度（如屏幕高度）
     * @param innerWidth 外部宽度（如屏幕宽度）
     * @param innerHeight 外部高度（如屏幕高度）
     * @return 居中位置
     */
    private Point calculateCenter(double outerWidth, double outerHeight, double innerWidth, double innerHeight){
        int x = (int) ((outerWidth - innerWidth) / 2);
        int y = (int) ((outerHeight - innerHeight) / 2);
        return new Point(x, y);
    }

    /**
     * 关闭当前窗口
     */
    public final void closeWindow(){
        this.action.doAction(this);
    }

    /**
     * 打开当前窗口，通过改变可见性直接展示当前窗口
     */
    public final void openWindow(){
        super.setVisible(true);
    }

    /**
     * 只能用openWindow()方法展示窗口，原本的可见性设置封掉
     */
    @Override
    public final void setVisible(boolean b) {
        throw new UnsupportedOperationException("请使用openWindow()方法展示窗口！");
    }

    /**
     * 抽象方法，由子类进行实现，返回true或是false决定是否关闭窗口
     * @return 是否关闭窗口
     */
    protected abstract boolean onClose();

    /**
     * 窗口初始化方法,所有的组件初始化操作都在这里实现
     * 一般放在构造方法中调用，而具体的调用时机由子类决定
     */
    protected abstract void initWindowContent();

    /**
     * 设定窗口的关闭动作，默认是调用dispose()方法，也可以设定为System.exit()
     */
    protected final void setDefaultCloseAction(CloseAction action){
        if(action == null)
            throw new IllegalArgumentException("窗口关闭动作不能为null!");
        this.action = action;
    }

    /**
     * 根据组件名称获取对应组件，并自动转换为对应类型
     * @param componentName 组件名称
     * @return 组件
     */
    @SuppressWarnings("unchecked")
    protected final <T extends Component> T getComponent(String componentName){
        return (T) componentMap.get(componentName);
    }

    /**
     * 重写原版的组件，创建方法更改为:
     * <code>
     *     this.addComponent("welcome.button.test", new JButton(), button -> {
     *          button.setLabel("我是按钮");
     *          button.setSize(100, 200);
     *          button.setLocation(200, 200);
     *     });
     * </code>
     * 集组件配置、添加组件到容器、添加到组件表一步到位。
     * @param name 组件的名称
     * @param component 待添加的组件
     * @param constraints 组件约束
     * @param consumer 组件配置在这里写
     */
    protected final <T extends Component> void addComponent(String name, T component, Object constraints, Consumer<T> consumer){
        if(consumer != null)
            consumer.accept(component);
        this.componentMap.put(name, component);
        this.add(component, constraints);
    }

    /**
     * 此方法是对指定的容器执行上述操作，用法相同。
     * @param target 指定容器
     * @param name 组件的名称
     * @param component 待添加的组件
     * @param consumer 组件配置在这里写
     */
    protected <T extends Component> void addComponent(Container target, String name, T component, Consumer<T> consumer){
        if(consumer != null)
            consumer.accept(component);
        this.componentMap.put(name, component);
        target.add(component);
    }

    /**
     * 此方法是对指定的容器执行上述操作，支持组件约束，用法相同。
     * @param target 指定容器
     * @param name 组件的名称
     * @param component 待添加的组件
     * @param constraints 组件约束
     * @param consumer 组件配置在这里写
     */
    protected <T extends Component> void addComponent(Container target, String name, T component, Object constraints, Consumer<T> consumer){
        if(consumer != null)
            consumer.accept(component);
        this.componentMap.put(name, component);
        target.add(component, constraints);
    }

    /**
     * 不添加组件到当前容器，仅仅将组件添加到组件表中进行管理
     * @param name 组件名称
     * @param component 组件
     */
    protected final <T extends Component> void mapComponent(String name, T component){
        this.componentMap.put(name, component);
        this.add(component);
    }
}
