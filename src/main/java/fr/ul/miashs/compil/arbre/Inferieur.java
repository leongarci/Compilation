/*
 *  License and Copyright:
 *
 *  This file is part of arbre  project.
 *
 * MIT License:
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * Copyright 2025 by IDMC, Université de Lorraine (azim)
 * All right reserved
 *
 */
package fr.ul.miashs.compil.arbre;

import java.util.ArrayList;

/**
 * Description :
 * @author Azim Roussanaly
 * Created at 28 févr. 2019
 */
public class Inferieur extends Noeud0 {
	//constructeurs
	public Inferieur() {
		setCat(Categories.INF);
		setFils(new ArrayList<>(2));
		this.getFils().add(0, null);
		this.getFils().add(1, null);
	}
	//methodes
	/**
	 * @return le fils gauche (par convention à l'indice 0)
	 */
	public Noeud getFilsGauche() {
		return getFils().get(0);
	}
	/**
	 * @return le fils droit (par convention à l'indice 1)
	 */
	public Noeud getFilsDroit() {
		return getFils().get(1);
	}
	/**
	 * Ajoute un fils gauche
	 */
	public void setFilsGauche(Noeud n) {
		getFils().set(0, n);
	}
	/**
	 * Ajoute un fils droit
	 */
	public void setFilsDroit(Noeud n) {
		getFils().set(1, n);
	}
}
