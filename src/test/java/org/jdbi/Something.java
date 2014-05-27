package org.jdbi;

public class Something
{
    private final int id;
    private final String name;

    public Something(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Something something = (Something) o;

        if (id != something.id) return false;
        if (name != null ? !name.equals(something.name) : something.name != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
