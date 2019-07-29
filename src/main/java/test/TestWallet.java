package test;

import com.waykichain.wallet.base.params.WaykiContractTxParams;
import com.waykichain.wallet.impl.LegacyWallet;
import com.waykichain.wallet.base.WaykiNetworkType;
import com.waykichain.wallet.base.params.WaykiCommonTxParams;
import com.waykichain.wallet.base.params.WaykiTestNetParams;
import com.waykichain.wallet.util.ContractUtil;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
/**

 */
public class TestWallet {

    public static void contextLoads() {

        /*CommonTx*/
        LegacyWallet wallet;
        wallet = new LegacyWallet();
        WaykiTestNetParams netParams =   WaykiTestNetParams.Companion.getInstance();
        String srcPrivKeyWiF = "YD9irHbVvvfqqX23ayJn4saCuvw7YFxsUqxp3gr79wibsGq4hJ9Z";  //对应地址 wWXYkAhNdNdv5LBEavQB1aUJeYqApNc2YW
        ECKey srcKey = DumpedPrivateKey.fromBase58(netParams, srcPrivKeyWiF).getKey();
        String srcAddress = LegacyAddress.fromPubKeyHash(netParams, srcKey.getPubKeyHash()).toString();

        String destAddr = "wgtZgd34GerwPLESV8AhdXtxMmAUNVgAV2" ;
        System.out.println("Send 1 wicc from: " + srcAddress  + " to: " + destAddr);
        WaykiCommonTxParams txParams = new WaykiCommonTxParams(WaykiNetworkType.TEST_NET, 1015593, 10662, 100000000,"672691-1",destAddr );
        txParams.signTx(srcKey);
        String tx = wallet.createCommonTransactionRaw(txParams);
        System.out.println("tx:"+tx);


        /*ContractTx*/
        //调用合约的参数，根据所要调用的合约方法进行拼接,以下只是一个例子
        String header="f2020000" ;
        String contractParam = header + "774b66717179527a4a614d4876317a77373759623452464572665a6861564c38636e";

        //对参数序列化
        byte[] contractByte = ContractUtil.Companion.hexString2binaryString(contractParam);

        //WaykiContractTxParams(from的公钥, 区块有效高度, 向合约转账WICC数量, from的regid, 合约regid, 序列化后的合约调用参数)
        WaykiContractTxParams contractTxParams = new WaykiContractTxParams(srcKey.getPubKey(),1016068, 100662,0,"672691-1","992449-2",contractByte);

        //对交易进行签名
        contractTxParams.signTx(srcKey);
        String contractTx = wallet.createContractTransactionRaw(contractTxParams);

        System.out.println("contractTx:"+contractTx);

    }

    public static void main(String[] args) {
        contextLoads();
    }
}
