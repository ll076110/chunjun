/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dtstack.flinkx.connector.kingbase.source;

import org.apache.flink.core.io.InputSplit;
import org.apache.flink.table.types.logical.RowType;

import com.dtstack.flinkx.connector.jdbc.source.JdbcInputFormat;
import com.dtstack.flinkx.connector.jdbc.util.JdbcUtil;
import com.dtstack.flinkx.connector.kingbase.converter.KingbaseRawTypeConverter;
import com.dtstack.flinkx.util.TableUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * @description:
 * @program: flinkx-all
 * @author: lany
 * @create: 2021/05/13 20:10
 */
public class KingbaseInputFormat extends JdbcInputFormat {

    public static final long serialVersionUID = 2L;

    @Override
    public void openInternal(InputSplit inputSplit) {
        super.openInternal(inputSplit);
        RowType rowType =
                TableUtil.createRowType(
                        columnNameList, columnTypeList, KingbaseRawTypeConverter::apply);
        setRowConverter(rowConverter ==null ? jdbcDialect.getColumnConverter(rowType, jdbcConf) : rowConverter);
    }

    @Override
    protected Pair<List<String>, List<String>> getTableMetaData() {
        return JdbcUtil.getTableMetaData(
                StringUtils.upperCase(jdbcConf.getSchema()),
                StringUtils.upperCase(jdbcConf.getTable()), dbConn);
    }
}
