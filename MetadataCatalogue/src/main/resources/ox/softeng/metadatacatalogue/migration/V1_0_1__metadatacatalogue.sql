--- Core database


SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE SCHEMA IF NOT EXISTS "Core";

ALTER SCHEMA "Core" OWNER TO metadatacatalogue;

SET search_path = "Core", pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE "User" (
	id UUID NOT NULL,
	"First Name" character varying(500),
	"Last Name" character varying(500),
	"Role" int,
	"Email Address" character varying(500) unique,
	"Password" bytea,
	"Salt" bytea
);

CREATE TABLE "UserGroup" (
	id UUID NOT NULL,
	"Name" character varying(500)
);

CREATE TABLE "User_UserGroup"(
	"User Id" UUID NOT NULL,
	"Group Id" UUID NOT NULL
);

CREATE TABLE "CatalogueItem" (
	id UUID NOT NULL,
	"Label" character varying(10485760),
	"Description" character varying(10485760),
	"Date/Time Created" timestamp,
	"Date/Time Last Updated" timestamp,
	"Created By" UUID NOT NULL,
	"DType" character varying(100)
	
);

CREATE TABLE "Metadata" (
	id UUID NOT NULL,
	"Catalogue Item" UUID NOT NULL,
	"Key" character varying(10485760),
	"Value" character varying(10485760)
);

CREATE TABLE "Sharable" (
	id UUID NOT NULL,
	"Readable By Everyone" BOOLEAN,
	"Writeable By Everyone" BOOLEAN
);

CREATE TABLE "ReadableByUsers" (
	"Sharable Id" UUID NOT NULL,
	"User Id" UUID NOT NULL
);

CREATE TABLE "ReadableByGroups" (
	"Sharable Id" UUID NOT NULL,
	"Group Id" UUID NOT NULL
);

CREATE TABLE "WriteableByUsers" (
	"Sharable Id" UUID NOT NULL,
	"User Id" UUID NOT NULL
);

CREATE TABLE "WriteableByGroups" (
	"Sharable Id" UUID NOT NULL,
	"Group Id" UUID NOT NULL
);

CREATE TABLE "DataModelComponent" (
	id UUID NOT NULL
);

CREATE TABLE "Annotation" (
	id UUID NOT NULL,
	"Annotated Component" UUID NOT NULL
);

CREATE TABLE "Link" (
	id UUID NOT NULL,
	"Source" UUID NOT NULL,
	"Target" UUID NOT NULL
);

CREATE TABLE "Classifier" (
	id UUID NOT NULL
);

CREATE TABLE "ClassifiedComponents" (
	id UUID NOT NULL,
	"Classifier Id" UUID NOT NULL,
	"DataModelComponent Id" UUID NOT NULL
);

CREATE TABLE "Finalisable" (
	id UUID NOT NULL,
	"Finalised" BOOLEAN,
	"Release Label" character varying(500)
);

