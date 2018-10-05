/*
 * Copyright 2014 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.avro.joda;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import org.junit.Assert;
import org.apache.avro.AvroUtils;
import org.apache.avro.LogicalType;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.test.TestRecord;
import org.apache.avro.test.TestRecord2;
import org.apache.avro.test.TestRecord3;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author zfarkas
 */
public class TestIsoDate {

    @Test
    public void testIsoDate() {
        Schema schema = Schema.create(Schema.Type.INT).withProp(LogicalType.LOGICAL_TYPE_PROP, "isodate");
        LogicalType type = LogicalTypes.fromSchema(schema);
        Assert.assertTrue("isodate logical type must be defined", type != null);
        System.out.println("Type is " + type);
    }

    @Test
    public void testSerialization() throws IOException {
        TestRecord record = TestRecord.newBuilder()
                .setDecimalVal(new BigDecimal("3.12345678"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(new BigDecimal("3.14"))
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .build();
        byte [] result = AvroUtils.writeAvroBin(record);
        TestRecord record2 = AvroUtils.readAvroBin(result, TestRecord.class, null);
        record.setDecimalVal(new BigDecimal("3.12345678"));
        Assert.assertEquals(record, record2);
        result = AvroUtils.writeAvroJson(record);
        System.out.println(new String(result, Charset.forName("UTF-8")));
        record2 = AvroUtils.readAvroJson(result, TestRecord.class);
        Assert.assertEquals(record, record2);
    }


    @Test
    public void testSerializationJson() throws IOException {
        TestRecord record = TestRecord.newBuilder()
                .setDecimalVal(new BigDecimal("3.14"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(new BigDecimal("3.14"))
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .setDateTimeStrVal(new DateTime(2014, 8, 15, 9, 0))
                .build();
        byte [] result = AvroUtils.writeAvroExtendedJson(record);
        System.out.println("Test JSON String: " + new String(result, "UTF-8"));
        TestRecord record2 = AvroUtils.readAvroExtendedJson(result, TestRecord.class);
        Assert.assertEquals(record, record2);
        result = AvroUtils.writeAvroExtendedJson(record);
        System.out.println(new String(result, Charset.forName("UTF-8")));
        record2 = AvroUtils.readAvroExtendedJson(result, TestRecord.class);
        Assert.assertEquals(record, record2);
    }





    @Test
    public void testSerialization2() throws IOException {
        TestRecord record = TestRecord.newBuilder()
                .setDecimalVal(new BigDecimal("3.14"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(null)
                .setDecimalVal4(2)
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .build();
        byte [] result = AvroUtils.writeAvroBin(record);
        TestRecord record2 = AvroUtils.readAvroBin(result, TestRecord.class, null);
        Assert.assertEquals(record, record2);
        result = AvroUtils.writeAvroJson(record);
        System.out.println(new String(result, Charset.forName("UTF-8")));
        record2 = AvroUtils.readAvroJson(result, TestRecord.class);
        Assert.assertEquals(record, record2);
    }

    @Test
    public void testSerializationFromIdl() throws IOException {
        TestRecord2 record = TestRecord2.newBuilder()
                .setDecimalVal(new BigDecimal("3.14"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(null)
                .setDecimalVal4(2)
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .build();
        byte [] result = AvroUtils.writeAvroBin(record);
        TestRecord2 record2 = AvroUtils.readAvroBin(result, TestRecord2.class, null);
        Assert.assertEquals(record, record2);
        result = AvroUtils.writeAvroJson(record);
        System.out.println(new String(result, Charset.forName("UTF-8")));
        record2 = AvroUtils.readAvroJson(result, TestRecord2.class);
        Assert.assertEquals(record, record2);
    }


    @Test
    public void testSerializationCompatibility() throws IOException {
        TestRecord2 record = TestRecord2.newBuilder()
                .setDecimalVal(new BigDecimal("3.14"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(null)
                .setDecimalVal4(2)
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .setIntervalVal(Interval.parse("2011-01-01/P1D"))
                .build();
        byte [] result = AvroUtils.writeAvroBin(record);
        TestRecord record2 = AvroUtils.readAvroBin(result, TestRecord.class, record.getSchema());
        Assert.assertEquals(record.getDecimalVal4(), record2.getDecimalVal4());
//        result = AvroUtils.writeAvroJson(record);
//        System.out.println(new String(result, Charset.forName("UTF-8")));
//        record2 = AvroUtils.readAvroJson(result, TestRecord.class);
//        Assert.assertEquals(record.getDecimalVal(), record2.getDecimalVal());
    }

    @Test
    public void testSerializationCompatibility3() throws IOException {
        TestRecord record = TestRecord.newBuilder()
                .setDecimalVal(new BigDecimal("3.14"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(null)
                .setDecimalVal4(2)
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .build();
        byte [] result = AvroUtils.writeAvroBin(record);
        TestRecord record2 = AvroUtils.readAvroBin(result, TestRecord.class, null);
        Assert.assertEquals(record.getDecimalVal4(), record2.getDecimalVal4());
//        result = AvroUtils.writeAvroJson(record);
//        System.out.println(new String(result, Charset.forName("UTF-8")));
//        record2 = AvroUtils.readAvroJson(result, TestRecord2.class);
//        Assert.assertEquals(record.getDecimalVal(), record2.getDecimalVal());
    }


    @Test
    @Ignore
    public void testSerializationCompatibility4() throws IOException {
        TestRecord3 record = TestRecord3.newBuilder()
                .setDecimalVal(new BigDecimal("3.14"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(null)
                .setDecimalVal4(2)
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .build();
        byte [] result = AvroUtils.writeAvroBin(record);
        TestRecord record2 = AvroUtils.readAvroBin(result, TestRecord.class, null);
        Assert.assertEquals(record.getDecimalVal4(), record2.getDecimalVal4());
//        result = AvroUtils.writeAvroJson(record);
//        System.out.println(new String(result, Charset.forName("UTF-8")));
//        record2 = AvroUtils.readAvroJson(result, TestRecord.class);
//        Assert.assertEquals(record.getDecimalVal(), record2.getDecimalVal());
    }

    @Test
    public void testSerializationCompatibility2() throws IOException {
        TestRecord record = TestRecord.newBuilder()
                .setDecimalVal(new BigDecimal("3.14"))
                .setDecimalVal2(new BigDecimal("3.14"))
                .setDecimalVal3(null)
                .setDecimalVal4(2)
                .setIntVal(0)
                .setDoubleVal(3.5).setDateVal(new LocalDate())
                .setDateVal2(new LocalDate())
                .setDateVal3(new LocalDate())
                .setDateTimeVal(new DateTime())
                .build();
        byte [] result = AvroUtils.writeAvroBin(record);
        TestRecord2 record2 = AvroUtils.readAvroBin(result, TestRecord2.class, record.getSchema());
        Assert.assertEquals(record.getDecimalVal4(), record2.getDecimalVal4());
//        result = AvroUtils.writeAvroJson(record);
//        System.out.println(new String(result, Charset.forName("UTF-8")));
//        record2 = AvroUtils.readAvroJson(result, TestRecord2.class);
//        Assert.assertEquals(record.getDecimalVal(), record2.getDecimalVal());
    }





}
