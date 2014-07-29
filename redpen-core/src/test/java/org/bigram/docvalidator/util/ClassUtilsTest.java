/**
 * redpen: a text inspection tool
 * Copyright (C) 2014 Recruit Technologies Co., Ltd. and contributors
 * (see CONTRIBUTORS.md)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bigram.docvalidator.util;

import org.junit.Test;
import java.lang.reflect.Type;
import static org.junit.Assert.assertEquals;

interface Generic<T> {
  T getValue();
}
class HasString implements Generic<String> {
  @Override
  public String getValue() {
    return "foobar";
  }
}
class HasInteger implements Generic<Integer> {
  @Override
  public Integer getValue() {
    return 1;
  }
}

public class ClassUtilsTest {
  @Test
  public void testGetParameterizedOfStringParameterrizedClass() {
    Type type = ClassUtils.getParameterizedClass(new HasString());
    assertEquals(String.class, type);
  }

  @Test
  public void testGetParameterizedOfIntegerParameterrizedClass() {
    Type type = ClassUtils.getParameterizedClass(new HasInteger());
    assertEquals(Integer.class, type);
  }

  @Test
  public void testGetParameterizedOfNonGenericClass() {
    Type type = ClassUtils.getParameterizedClass("");
    assertEquals(null, type);
  }

  @Test
  public void testGetParameterizedOfNull() {
    Type type = ClassUtils.getParameterizedClass(null);
    assertEquals(null, type);
  }

}
