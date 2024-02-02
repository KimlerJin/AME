
package com.ame.uidgenerator;

import com.ame.dao.BaseDao;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class WorkerNodeDao extends BaseDao {

    @Transactional
    public void save(WorkerNodeEntity workerNodeEntity) {
        getHibernateTool().execute(session -> {
            try {
                String hql = "from WorkerNodeEntity t1 where t1.id =(select max(t2.id) from WorkerNodeEntity t2)";
                Query<WorkerNodeEntity> query = session.createQuery(hql, WorkerNodeEntity.class);
                List<WorkerNodeEntity> list = query.list();
                if (list.size() > 0) {
                    workerNodeEntity.setId(list.get(0).getId() + 1);
                } else {
                    workerNodeEntity.setId(1);
                }
                session.save(workerNodeEntity);
            } catch (Exception e) {
                throw e;
            }
            return null;
        });
    }

}
