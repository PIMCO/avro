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

import java.util.Collections;
import java.util.Set;
import org.apache.avro.AbstractLogicalType;
import org.apache.avro.Schema;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class IsoInstant extends AbstractLogicalType {


  public static final DateTimeFormatter FMT = ISODateTimeFormat.dateTime().withZoneUTC();

  public static final DateTimeFormatter PARSER_FMT = ISODateTimeFormat.dateTimeParser().withOffsetParsed();

  public IsoInstant(Schema.Type type) {
    super(type, Collections.EMPTY_SET, "isoinstant", Collections.EMPTY_MAP);
    // validate the type
    if (type != Schema.Type.LONG && type != Schema.Type.STRING) {
      throw new IllegalArgumentException(
              "Logical type " + this + " must be backed by long or string");
    }
  }

  @Override
  public void validate(Schema schema) {
    Schema.Type st = schema.getType();
    // validate the type
    if (st != Schema.Type.LONG && st != Schema.Type.STRING) {
      throw new IllegalArgumentException(
              "Logical type " + this + " must be backed by long or string");
    }
  }

  @Override
  public Set<String> reserved() {
    return Collections.EMPTY_SET;
  }

  @Override
  public Class<?> getLogicalJavaType() {
    return Instant.class;
  }


  @Override
  public Object deserialize(Object object) {
    switch (type) {
      case LONG:
        return new Instant((Long) object);
      case STRING:
        return new Instant(PARSER_FMT.parseMillis((String) object));
      default:
        throw new IllegalStateException("Unsupported type: " + type);
    }
  }

  @Override
  public Object serialize(Object object) {
    switch (type) {
      case LONG:
        return ((Instant) object).getMillis();
      case STRING:
        return FMT.print((Instant) object);
      default:
        throw new IllegalStateException("Unsupported type: " + type);
    }
  }

}
