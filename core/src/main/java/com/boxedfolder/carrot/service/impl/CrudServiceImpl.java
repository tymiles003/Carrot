/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.OrderedRepository;
import com.boxedfolder.carrot.service.CrudService;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public abstract class CrudServiceImpl<T extends AbstractEntity, S extends OrderedRepository<T>>
        implements CrudService<T>
{
    protected S repository;

    @Override
    public T find(Long id) {
        T object = repository.findOne(id);
        if (object == null) {
            throw new GeneralExceptions.NotFoundException();
        }

        return object;
    }

    @Override
    public List<T> findAll() {
        return (List<T>)repository.findAllByOrderByDateCreatedDesc();
    }

    @Override
    public List<T> findAll(int page, int size) {
        return findAll(new PageRequest(page, size));
    }

    @Override
    public List<T> findAll(Pageable page) {
        return repository.findAll(page).getContent();
    }

    @Override
    public T save(T object) {
        return repository.save(object);
    }

    @Override
    public void delete(Long id) {
        T object = repository.findOne(id);
        if (object == null) {
            throw new GeneralExceptions.NotFoundException();
        }

        repository.delete(object);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Inject
    public void setRepository(S repository) {
        this.repository = repository;
    }
}