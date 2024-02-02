
package com.ame.uidgenerator;

import com.ame.lock.LockService;
import com.ame.uidgenerator.util.DockerUtils;
import com.ame.uidgenerator.worker.WorkerIdAssigner;
import com.ame.uidgenerator.worker.WorkerNodeType;
import com.ame.util.net.NetUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

public class DisposableWorkerIdAssigner implements WorkerIdAssigner {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(DisposableWorkerIdAssigner.class);

    private WorkerNodeDao workerNodeDAO;

    private LockService lockService;

    public DisposableWorkerIdAssigner(WorkerNodeDao workerNodeDAO, LockService lockService) {
        this.workerNodeDAO = workerNodeDAO;
        this.lockService = lockService;
    }

    /**
     * Assign worker id base on database.
     * <p>
     * If there is host name & port in the environment, we considered that the node runs in Docker container<br>
     * Otherwise, the node runs on an actual machine.
     *
     * @return assigned worker id
     */
    @Override
    public long assignWorkerId() {
        WorkerNodeEntity workerNodeEntity = buildWorkerNode();
        Lock lock = lockService.obtain(DisposableWorkerIdAssigner.class.getName());
        try {
            lock.lock();
            workerNodeDAO.save(workerNodeEntity);
        } finally {
            lock.unlock();
        }
        return workerNodeEntity.getId();
    }

    /**
     * Build worker node entity by IP and PORT
     */
    private WorkerNodeEntity buildWorkerNode() {

        WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
        if (DockerUtils.isDocker()) {
            workerNodeEntity.setType(WorkerNodeType.CONTAINER.value());
            workerNodeEntity.setHostName(DockerUtils.getDockerHost());
            workerNodeEntity.setPort(DockerUtils.getDockerPort());

        } else {
            workerNodeEntity.setType(WorkerNodeType.ACTUAL.value());
            workerNodeEntity.setHostName(NetUtils.getLocalIpAddress());
            workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(0, 100000));
        }
        return workerNodeEntity;
    }

}
