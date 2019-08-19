# NetworkListener
一个完美适配Android每个版本的网络监听框架。
使用EventBus原理实现，网络发生变化时通过注解方法实时返回

使用方式如下：
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkManager.getDefault().register(this)


    }

    @Network(netType = NetType.WIFI)
    fun changeNetwork(netType: NetType) {

        when (netType) {
            NetType.WIFI -> {
                Log.e(LOG_TAG, "wifi链接")
            }
            NetType.CMNET -> {
                Log.e(LOG_TAG, "CMNET链接")
            }
            NetType.CMWAP -> {
                Log.e(LOG_TAG, "CMWAP链接")
            }
            NetType.NONE -> {
                Log.e(LOG_TAG, "NONE链接")
            }
            NetType.AUTO -> {
                Log.e(LOG_TAG, "AUTO链接")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.getDefault().unRegister(this)
    }}
		changeNetwork在这个方法的注解参数中，需要什么类型的网络返回就定义什么类型的网络返回。
		
		
		
