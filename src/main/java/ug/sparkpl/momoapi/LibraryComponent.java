package ug.sparkpl.momoapi;

import dagger.Component;

import javax.inject.Singleton;


@Singleton
@Component(modules = {LibraryModule.class})
public interface LibraryComponent extends LibraryGraph {
}
