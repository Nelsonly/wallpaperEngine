package com.nelson.myapplication;

public abstract class BaseActivity<P extends IPresent>  extends AppCompatActivity implements IView<P> {
    private Unbinder unbinder;
    private P present;
    private Context context;
    /**
     * 加载弹窗
     */
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        this.context = this;
        initData(savedInstanceState);
        if(getLayoutId() > 0){
            bindUI(null);
        }
    }

    /**
     * 绑定UI
     * @param rootView
     */
    @CallSuper
    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeKit.bind(this);
    }

    /**
     * 获取present对象
     * @return
     */
    @Override
    public P getPresent() {
        if (present == null) {
            present = newPresent();
            if (present != null) {
                present.attachView(this);
            }
        }
        return present;
    }

    /**
     * 创建present
     * @return
     */
    @Override
    public P newPresent() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        if (present != null) {
            present.detachView(this);
        }

        KnifeKit.unbind(unbinder);
        present = null;
        super.onDestroy();
    }