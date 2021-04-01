package client.model.online;

import client.FileCluster;
import client.model.Model;
import client.utils.SocketIPC;
import client.utils.TunableParameters;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.Socket;

public class Gradient extends Model {

    public Gradient() {
        super();
    }

    private static final Log LOG = LogFactory.getLog(Gradient.class);

    public TunableParameters runModel (FileCluster chunk, TunableParameters tunableParameters, double throughput,
                                       double[] relaxation_rates) {
        int nextCC = -1;
        try {
            String fullMessage = Double.toString(throughput/(1024*1024));
            out.println(fullMessage);
            nextCC = Integer.parseInt(in.readLine());
            System.out.println("Received:" + nextCC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TunableParameters.Builder().setConcurrency(nextCC).setParallelism(8).setPipelining(3).build();
    }

    public TunableParameters requestNextParameters() {
        try {
            int nextCC = Integer.parseInt(in.readLine());
            System.out.println("Received:" + nextCC);
            return new TunableParameters.Builder().setConcurrency(nextCC).build();
        }
        catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

    }
}
