/* 
 * Copyright 2017 Martin Chovanec.
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
package cz.chovanecm.pascal.ast;

/**
 *
 * @author martin
 */
public interface VariableInterface extends ExpressionInterface {

    public String getName();

    abstract String getString();

    public Integer getInteger();

    public Double getReal();

    public void setString(String value);

    public void setInteger(Integer value);

    public void setReal(Double value);

    public void setName(String id);

    @Override
    public boolean equals(Object object);

    @Override
    public int hashCode();

    public void setValue(Object value);

}
