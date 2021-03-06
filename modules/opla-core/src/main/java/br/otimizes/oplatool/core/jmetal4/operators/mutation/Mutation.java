//  Mutation.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package br.otimizes.oplatool.core.jmetal4.operators.mutation;

import br.otimizes.oplatool.core.jmetal4.core.Operator;

import java.util.Map;

/**
 * This class represents the super class of all the mutations operators
 */
public abstract class Mutation extends Operator {

    private static final long serialVersionUID = -4307112630484321270L;

    public Mutation(Map<String, Object> parameters) {
        super(parameters);
    }
} // Mutation
