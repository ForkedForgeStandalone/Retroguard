/* ===========================================================================
 * $RCSfile: InnerClassesAttrInfo.java,v $
 * ===========================================================================
 *
 * RetroGuard -- an obfuscation package for Java classfiles.
 *
 * Copyright (c) 1998-2006 Mark Welsh (markw@retrologic.com)
 *
 * This program can be redistributed and/or modified under the terms of the
 * Version 2 of the GNU General Public License as published by the Free
 * Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */

package COM.rl.obf.classfile;

import java.io.*;
import java.util.*;

/**
 * Representation of an attribute.
 * 
 * @author Mark Welsh
 */
public class InnerClassesAttrInfo extends AttrInfo
{
    // Constants -------------------------------------------------------------


    // Fields ----------------------------------------------------------------
    private int u2numberOfClasses;
    private InnerClassesInfo[] classes;


    // Class Methods ---------------------------------------------------------


    // Instance Methods ------------------------------------------------------
    protected InnerClassesAttrInfo(ClassFile cf, int attrNameIndex, int attrLength)
    {
        super(cf, attrNameIndex, attrLength);
    }

    /** Return the String name of the attribute; over-ride this in sub-classes. */
    @Override
    protected String getAttrName() throws Exception
    {
        return ClassConstants.ATTR_InnerClasses;
    }

    /** Return the array of inner classes data. */
    protected InnerClassesInfo[] getInfo() throws Exception
    {
        return this.classes;
    }

    /** Check for Utf8 references in the 'info' data to the constant pool and mark them. */
    @Override
    protected void markUtf8RefsInInfo(ConstantPool pool) throws Exception
    {
        for (int i = 0; i < this.classes.length; i++)
        {
            this.classes[i].markUtf8Refs(pool);
        }
    }

    /** Read the data following the header. */
    @Override
    protected void readInfo(DataInput din) throws Exception
    {
        this.u2numberOfClasses = din.readUnsignedShort();
        this.classes = new InnerClassesInfo[this.u2numberOfClasses];
        for (int i = 0; i < this.u2numberOfClasses; i++)
        {
            this.classes[i] = InnerClassesInfo.create(din);
        }
    }

    /** Export data following the header to a DataOutput stream. */
    @Override
    public void writeInfo(DataOutput dout) throws Exception
    {
        dout.writeShort(this.u2numberOfClasses);
        for (int i = 0; i < this.u2numberOfClasses; i++)
        {
            this.classes[i].write(dout);
        }
    }

    /** Do necessary name remapping. */
    @Override
    protected void remap(ClassFile cf, NameMapper nm) throws Exception
    {
        for (int i = 0; i < this.u2numberOfClasses; i++)
        {
            this.classes[i].remap(cf, nm);
        }
    }
}
