/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.ame.uidgenerator;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.time.ZonedDateTime;

@Entity
@Table(name = "SYS_UID_WORKER_NODE")
public class WorkerNodeEntity {

    @Id
    @Column(name = "ID")
    private long id;

    /**
     * Type of CONTAINER: HostName, ACTUAL : IP.
     */
    @Column(name = "HOST_NAME", length = 64)
    private String hostName;

    /**
     * Type of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
     */
    @Column(name = "PORT", length = 64)
    private String port;

    /**
     * type of {@link WorkerNodeType}
     */
    @Column(name = "TYPE")
    private int type;

    /**
     * Worker launch date, default now
     */
    @Column(name = "LAUNCH_DATE")
    private ZonedDateTime launchDate = ZonedDateTime.now();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ZonedDateTime getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(ZonedDateTime launchDate) {
        this.launchDate = launchDate;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
