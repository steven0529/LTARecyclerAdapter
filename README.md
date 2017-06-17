# LTActionRecyclerAdapter
A RecyclerView Adapter with leading or trailing action view.

# Usage

1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
 repositories {
    ...
    maven { url "https://jitpack.io" }
 }
}
```
2. Add the dependency
```groovy
dependencies {
    compile 'com.github.steven0529:LTARecyclerAdapter:-SNAPSHOT'
}
```
3. Extend LTActionViewHolder this acts as the viewholder of your action button/view
```java
public class SampleActionViewHolder extends LTActionViewHolder {
    public SampleActionViewHolder(View itemView) {
        super(itemView);
    }
}
```
4. (Optional) Extend LTAItemViewHolder, this acts as the viewholder of your items, you can use the default
RecyclerView.ViewHolder class
```java
public class ImageViewHolder extends LTActionItemViewHolder {
    ImageView ivImage;
    public ImageViewHolder(View itemView) {
        super(itemView);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
    }
}
```
5. Extend the LTARecyclerAdapter<T, S extends LTActionViewHolder>, T is the data type of your list items,
S is the action button/view viewholder

6. Your custom Adapter must implement 3 methods (
```java
    /**
    * Acts as the normal onBindViewHolder of the RecyclerView.Adapter wrapped other functionalities
    */
    @Override
    public void onBindAdaptingRecyclerViewHolder(LTActionItemViewHolder LTActionItemViewHolder, int position) { 
    }

    /**
    * Acts as the normal onCreateViewHolder of the RecyclerView.Adapter but renders the LTActionViewHolder
    */
    @Override
    public ImageLTActionViewHolder renderActionViewHolder(ViewGroup parent) {
        
    }
    /**
    * Acts as the normal onCreateViewHolder of the RecyclerView.Adapter but renders the LTAItemViewHolder
    */
    @Override
    public RecyclerView.ViewHolder renderItemViewHolder(ViewGroup parent) {
        return null;
    }
```
7. LTARecyclerAdapter has 2 required parameters and 1 optional on its constructor
- ActionPosition is an enum for ActionPosition.LEADING or ActionPosition.TRAILING
- List<T> data for the data list
- (Optional) maxItems for the max number of items to be displayed before hiding the action button
```java
public LTARecyclerAdapter(List<T> data, ActionPosition actionPosition, int maxItems)
```
