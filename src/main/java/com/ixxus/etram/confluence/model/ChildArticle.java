/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.confluence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ChildArticle extends Article {
    private List<Ancestor> ancestors;
}
