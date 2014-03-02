package femtocraft;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FemtocraftDataUtils {
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Saveable {
		public boolean world() default true;

		public boolean desc() default false;

		public boolean item() default false;
	}

	public static enum EnumSaveType {
		WORLD, DESCRIPTION, ITEM
	}

	public static void saveObjectToNBT(NBTTagCompound compound, Object obj,
			EnumSaveType type) {
		ArrayList<Field> fields = new ArrayList<Field>();

		Class objclass = obj.getClass();
		while (objclass != null) {
			fields.addAll(Arrays.asList(objclass.getDeclaredFields()));
			objclass = objclass.getSuperclass();
		}

		for (Field field : fields) {
			boolean flag = field.isAccessible();
			if (!flag)
				field.setAccessible(true);
			saveToNBT(compound, field, obj, type);
			if (!flag)
				field.setAccessible(false);
		}
	}

	public static void saveToNBT(NBTTagCompound compound, Field saveable,
			Object obj, EnumSaveType type) {
		Saveable asave = saveable.getAnnotation(Saveable.class);
		if (asave == null)
			return;

		switch (type) {
		case WORLD:
			if (!asave.world())
				return;
			break;
		case DESCRIPTION:
			if (!asave.desc())
				return;
			break;
		case ITEM:
			if (!asave.item())
				return;
			break;
		}

		try {
			if (saveable.getType() == int.class) {
				compound.setInteger(saveable.getName(), saveable.getInt(obj));
				return;
			}
			if (saveable.getType() == int[].class) {
				compound.setIntArray(saveable.getName(),
						(int[]) saveable.get(obj));
				return;
			}
			if (saveable.getType() == boolean.class) {
				compound.setBoolean(saveable.getName(),
						saveable.getBoolean(obj));
				return;
			}
			if (saveable.getType() == boolean[].class) {
				NBTTagCompound array = new NBTTagCompound();
				boolean[] barr = (boolean[]) saveable.get(obj);
				array.setInteger("size", barr.length);
				int post = 0;
				for (boolean bool : barr) {
					array.setBoolean(saveable.getName() + post, bool);
				}
				compound.setCompoundTag(saveable.getName(), array);
			}
			if (saveable.getType() == float.class) {
				compound.setFloat(saveable.getName(), saveable.getFloat(obj));
				return;
			}
			if (saveable.getType() == float[].class) {
				NBTTagCompound array = new NBTTagCompound();
				float[] farr = (float[]) saveable.get(obj);
				array.setInteger("size", farr.length);
				int post = 0;
				for (float floa : farr) {
					array.setFloat(saveable.getName() + post, floa);
				}
				compound.setCompoundTag(saveable.getName(), array);
			}
			if (saveable.getType() == double.class) {
				compound.setDouble(saveable.getName(), saveable.getDouble(obj));
				return;
			}
			if (saveable.getType() == double[].class) {
				NBTTagCompound array = new NBTTagCompound();
				double[] darr = (double[]) saveable.get(obj);
				array.setInteger("size", darr.length);
				int post = 0;
				for (double doub : darr) {
					array.setDouble(saveable.getName() + post, doub);
				}
				compound.setCompoundTag(saveable.getName(), array);
			}
			if (saveable.getType() == String.class) {
				compound.setString(saveable.getName(),
						(String) saveable.get(obj));
				return;
			}
			if (saveable.getType() == String[].class) {
				NBTTagCompound array = new NBTTagCompound();
				String[] darr = (String[]) saveable.get(obj);
				array.setInteger("size", darr.length);
				int post = 0;
				for (String str : darr) {
					array.setString(saveable.getName() + post, str);
				}
				compound.setCompoundTag(saveable.getName(), array);
			}
			if (saveable.getType() == short.class) {
				compound.setShort(saveable.getName(), saveable.getShort(obj));
				return;
			}
			if (saveable.getType() == short[].class) {
				NBTTagCompound array = new NBTTagCompound();
				short[] darr = (short[]) saveable.get(obj);
				array.setInteger("size", darr.length);
				int post = 0;
				for (short str : darr) {
					array.setShort(saveable.getName() + post, str);
				}
				compound.setCompoundTag(saveable.getName(), array);
			}
			if (saveable.getType() == long.class) {
				compound.setLong(saveable.getName(), saveable.getLong(obj));
				return;
			}
			if (saveable.getType() == long[].class) {
				NBTTagCompound array = new NBTTagCompound();
				long[] darr = (long[]) saveable.get(obj);
				array.setInteger("size", darr.length);
				int post = 0;
				for (long str : darr) {
					array.setLong(saveable.getName() + post, str);
				}
				compound.setCompoundTag(saveable.getName(), array);
			}
			if (saveable.getType() == byte.class) {
				compound.setByte(saveable.getName(), saveable.getByte(obj));
			}
			if (saveable.getType() == byte[].class) {
				compound.setByteArray(saveable.getName(),
						(byte[]) saveable.get(obj));
				return;
			}
			if (saveable.getType() == NBTTagCompound.class) {
				compound.setCompoundTag(saveable.getName(),
						(NBTTagCompound) saveable.get(obj));
				return;
			}
			if (saveable.getType() == NBTTagList.class) {
				compound.setTag(saveable.getName(),
						(NBTTagList) saveable.get(obj));
				return;
			}
			if (saveable.getType() == ItemStack.class) {
				NBTTagCompound item = new NBTTagCompound();
				ItemStack stack = (ItemStack) saveable.get(obj);
				stack.writeToNBT(item);
				compound.setCompoundTag(saveable.getName(), item);
				return;
			}
			if (saveable.getType() == ItemStack[].class) {
				NBTTagCompound array = new NBTTagCompound();
				NBTTagList list = new NBTTagList();
				ItemStack[] isarr = (ItemStack[]) saveable.get(obj);
				array.setInteger("size", isarr.length);

				for (int i = 0; i < isarr.length; ++i) {
					ItemStack stack = isarr[i];
					if (stack == null)
						continue;

					NBTTagCompound scomp = new NBTTagCompound();
					scomp.setInteger("index", i);
					stack.writeToNBT(scomp);
					list.appendTag(scomp);
				}
				array.setTag("items", list);
				compound.setCompoundTag(saveable.getName(), array);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadObjectFromNBT(NBTTagCompound compound, Object obj,
			EnumSaveType type) {
		ArrayList<Field> fields = new ArrayList<Field>();

		Class objclass = obj.getClass();
		while (objclass != null) {
			fields.addAll(Arrays.asList(objclass.getDeclaredFields()));
			objclass = objclass.getSuperclass();
		}

		for (Field field : fields) {
			boolean flag = field.isAccessible();
			if (!flag)
				field.setAccessible(true);
			loadFromNBT(compound, field, obj, type);
			if (!flag)
				field.setAccessible(false);
		}
	}

	private static void loadFromNBT(NBTTagCompound compound, Field saveable,
			Object obj, EnumSaveType type) {
		Saveable asave = saveable.getAnnotation(Saveable.class);
		if (asave == null)
			return;

		switch (type) {
		case WORLD:
			if (!asave.world())
				return;
			break;
		case DESCRIPTION:
			if (!asave.desc())
				return;
			break;
		case ITEM:
			if (!asave.item())
				return;
			break;
		}

		try {
			if (saveable.getType() == int.class) {
				saveable.setInt(obj, compound.getInteger(saveable.getName()));
				return;
			}
			if (saveable.getType() == int[].class) {
				saveable.set(obj, compound.getIntArray(saveable.getName()));
				return;
			}
			if (saveable.getType() == boolean.class) {
				saveable.setBoolean(obj,
						compound.getBoolean(saveable.getName()));
				return;
			}
			if (saveable.getType() == boolean[].class) {
				NBTTagCompound array = compound.getCompoundTag(saveable
						.getName());
				boolean[] ret = new boolean[array.getInteger("size")];
				for (int i = 0; i < ret.length; ++i) {
					ret[i] = array.getBoolean(saveable.getName() + i);
				}
				saveable.set(obj, ret);
				return;
			}
			if (saveable.getType() == float.class) {
				saveable.setFloat(obj, compound.getFloat(saveable.getName()));
				return;
			}
			if (saveable.getType() == float[].class) {
				NBTTagCompound array = compound.getCompoundTag(saveable
						.getName());
				float[] ret = new float[array.getInteger("size")];
				for (int i = 0; i < ret.length; ++i) {
					ret[i] = array.getFloat(saveable.getName() + i);
				}
				saveable.set(obj, ret);
				return;
			}
			if (saveable.getType() == double.class) {
				saveable.setDouble(obj, compound.getDouble(saveable.getName()));
				return;
			}
			if (saveable.getType() == double[].class) {
				NBTTagCompound array = compound.getCompoundTag(saveable
						.getName());
				double[] ret = new double[array.getInteger("size")];
				for (int i = 0; i < ret.length; ++i) {
					ret[i] = array.getDouble(saveable.getName() + i);
				}
				saveable.set(obj, ret);
				return;
			}
			if (saveable.getType() == String.class) {
				saveable.set(obj, compound.getString(saveable.getName()));
				return;
			}
			if (saveable.getType() == String[].class) {
				NBTTagCompound array = compound.getCompoundTag(saveable
						.getName());
				String[] ret = new String[array.getInteger("size")];
				for (int i = 0; i < ret.length; ++i) {
					ret[i] = array.getString(saveable.getName() + i);
				}
				saveable.set(obj, ret);
				return;
			}
			if (saveable.getType() == short.class) {
				saveable.setShort(obj, compound.getShort(saveable.getName()));
				return;
			}
			if (saveable.getType() == short[].class) {
				NBTTagCompound array = compound.getCompoundTag(saveable
						.getName());
				short[] ret = new short[array.getInteger("size")];
				for (int i = 0; i < ret.length; ++i) {
					ret[i] = array.getShort(saveable.getName() + i);
				}
				saveable.set(obj, ret);
				return;
			}
			if (saveable.getType() == long.class) {
				saveable.setLong(obj, compound.getLong(saveable.getName()));
				return;
			}
			if (saveable.getType() == long[].class) {
				NBTTagCompound array = compound.getCompoundTag(saveable
						.getName());
				long[] ret = new long[array.getInteger("size")];
				for (int i = 0; i < ret.length; ++i) {
					ret[i] = array.getLong(saveable.getName() + i);
				}
				saveable.set(obj, ret);
				return;
			}
			if (saveable.getType() == byte.class) {
				saveable.setByte(obj, compound.getByte(saveable.getName()));
				return;
			}
			if (saveable.getType() == byte[].class) {
				saveable.set(obj, compound.getByteArray(saveable.getName()));
				return;
			}
			if (saveable.getType() == NBTTagCompound.class) {
				saveable.set(obj, compound.getCompoundTag(saveable.getName()));
				return;
			}
			if (saveable.getType() == NBTTagList.class) {
				saveable.set(obj, compound.getTagList(saveable.getName()));
				return;
			}
			if (saveable.getType() == ItemStack.class) {
				NBTTagCompound item = compound.getCompoundTag(saveable
						.getName());
				saveable.set(obj, ItemStack.loadItemStackFromNBT(item));
				return;
			}
			if (saveable.getType() == ItemStack[].class) {
				NBTTagCompound array = compound.getCompoundTag(saveable
						.getName());
				NBTTagList list = array.getTagList("items");
				ItemStack[] retarray = new ItemStack[array.getInteger("size")];
				Arrays.fill(retarray, null);
				for (int i = 0; i < list.tagCount(); ++i) {
					NBTTagCompound item = (NBTTagCompound) list.tagAt(i);
					retarray[item.getInteger("index")] = ItemStack
							.loadItemStackFromNBT(item);
				}
				saveable.set(obj, retarray);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
