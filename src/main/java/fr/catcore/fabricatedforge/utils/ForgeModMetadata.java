package fr.catcore.fabricatedforge.utils;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.*;
import net.fabricmc.loader.impl.metadata.EntrypointMetadata;
import net.fabricmc.loader.impl.metadata.LoaderModMetadata;
import net.fabricmc.loader.impl.metadata.NestedJarEntry;
import net.fabricmc.loader.impl.util.version.StringVersion;

import java.util.*;

public class ForgeModMetadata implements LoaderModMetadata {

    private final String modid;
    private final String modName;
    private final String version;
    private final String description;
    private final String logo;

    protected ForgeModMetadata(String modid, String modName, String version, String description, String logo) {
        this.modid = modid;
        this.modName = modName;
        this.version = version;
        this.description = description;
        this.logo = logo;
    }

    @Override
    public String getType() {
        return "forge";
    }

    @Override
    public String getId() {
        return this.modid;
    }

    @Override
    public Collection<String> getProvides() {
        return Collections.emptyList();
    }

    @Override
    public Version getVersion() {
        return new StringVersion(this.version.isEmpty() ? "1.0.0" : this.version);
    }

    @Override
    public ModEnvironment getEnvironment() {
        return ModEnvironment.UNIVERSAL;
    }

    @Override
    public Collection<ModDependency> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return this.modName;
    }

    @Override
    public String getDescription() {
        return this.description.isEmpty() ? "This is a converted Forge mod." : this.description;
    }

    @Override
    public Collection<Person> getAuthors() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Person> getContributors() {
        return Collections.emptyList();
    }

    @Override
    public ContactInformation getContact() {
        return ContactInformation.EMPTY;
    }

    @Override
    public Collection<String> getLicense() {
        return Collections.emptyList();
    }

    @Override
    public Optional<String> getIconPath(int size) {
        return Optional.ofNullable(this.logo);
    }

    @Override
    public boolean containsCustomValue(String key) {
        return false;
    }

    @Override
    public CustomValue getCustomValue(String key) {
        return null;
    }

    @Override
    public Map<String, CustomValue> getCustomValues() {
        return Maps.newHashMap();
    }

    @Override
    public boolean containsCustomElement(String key) {
        return false;
    }

    @Override
    public int getSchemaVersion() {
        return 0;
    }

    @Override
    public Map<String, String> getLanguageAdapterDefinitions() {
        return Maps.newHashMap();
    }

    @Override
    public Collection<NestedJarEntry> getJars() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getMixinConfigs(EnvType type) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessWidener() {
        return null;
    }

    @Override
    public boolean loadsInEnvironment(EnvType type) {
        return true;
    }

    @Override
    public Collection<String> getOldInitializers() {
        return Collections.emptyList();
    }

    @Override
    public List<EntrypointMetadata> getEntrypoints(String type) {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getEntrypointKeys() {
        return Collections.emptyList();
    }

    @Override
    public void emitFormatWarnings() {

    }

    @Override
    public void setVersion(Version version) {

    }

    @Override
    public void setDependencies(Collection<ModDependency> dependencies) {

    }
}