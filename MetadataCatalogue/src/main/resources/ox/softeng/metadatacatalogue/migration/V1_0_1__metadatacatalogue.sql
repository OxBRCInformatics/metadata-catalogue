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

ALTER TABLE "User" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "User" ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);

CREATE TABLE "UserGroup" (
	id UUID NOT NULL,
	"Name" character varying(500)
);

ALTER TABLE "UserGroup" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "UserGroup" ADD CONSTRAINT "UserGroup_pkey" PRIMARY KEY (id);

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

ALTER TABLE "CatalogueItem" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "CatalogueItem" ADD CONSTRAINT "CatalogueItem_pkey" PRIMARY KEY (id);

CREATE TABLE "Metadata" (
	id UUID NOT NULL,
	"Catalogue Item" UUID NOT NULL,
	"Key" character varying(10485760),
	"Value" character varying(10485760)
);

ALTER TABLE "Metadata" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Metadata" ADD CONSTRAINT "Metadata_pkey" PRIMARY KEY (id);


CREATE TABLE "Sharable" (
	id UUID NOT NULL,
	"Readable By Everyone" BOOLEAN,
	"Writeable By Everyone" BOOLEAN
);

ALTER TABLE "Sharable" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Sharable" ADD CONSTRAINT "Sharable_pkey" PRIMARY KEY (id);


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

ALTER TABLE "DataModelComponent" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "DataModelComponent" ADD CONSTRAINT "DataModelComponent_pkey" PRIMARY KEY (id);



CREATE TABLE "Annotation" (
	id UUID NOT NULL,
	"Annotated Component" UUID NOT NULL
);

ALTER TABLE "Annotation" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Annotation" ADD CONSTRAINT "Annotation_pkey" PRIMARY KEY (id);



CREATE TABLE "Link" (
	id UUID NOT NULL,
	"Source" UUID NOT NULL,
	"Target" UUID NOT NULL
);

ALTER TABLE "Link" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Link" ADD CONSTRAINT "Link_pkey" PRIMARY KEY (id);



CREATE TABLE "Classifier" (
	id UUID NOT NULL
);

ALTER TABLE "Classifier" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Classifier" ADD CONSTRAINT "Classifier_pkey" PRIMARY KEY (id);



CREATE TABLE "ClassifiedComponents" (
	"Classifier Id" UUID NOT NULL,
	"DataModelComponent Id" UUID NOT NULL
);


CREATE TABLE "Finalisable" (
	id UUID NOT NULL,
	"Finalised" BOOLEAN,
	"Release Label" character varying(500)
);

ALTER TABLE "Finalisable" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Finalisable" ADD CONSTRAINT "Finalisable_pkey" PRIMARY KEY (id);



CREATE TABLE "DataModel" (
	id UUID NOT NULL,
	"Author" character varying(500),
	"Organization" character varying(500),
	"Type" character varying(100)
);

ALTER TABLE "DataModel" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "DataModel" ADD CONSTRAINT "DataModel_pkey" PRIMARY KEY (id);


CREATE TABLE "DataModel_ImportsFrom" (
	"DataModel Id" UUID NOT NULL,
	"Imported DataModel Id" UUID NOT NULL
);

CREATE TABLE "DataClass" (
	id UUID NOT NULL,
	"Belongs To Model" UUID NOT NULL,
	"Parent Model" UUID,
	"Parent Class" UUID,
	"Path" character varying(1000)
);

ALTER TABLE "DataClass" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "DataClass" ADD CONSTRAINT "DataClass_pkey" PRIMARY KEY (id);



CREATE TABLE "DataElement" (
	id UUID NOT NULL,
	"Belongs To Model" UUID NOT NULL,
	"DataType" UUID NOT NULL,
	"Path" character varying(1000),
	"Parent Class" UUID
);

ALTER TABLE "DataElement" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "DataElement" ADD CONSTRAINT "DataElement_pkey" PRIMARY KEY (id);


CREATE TABLE "DataType" (
	id UUID NOT NULL,
	"Belongs To Model" UUID NOT NULL,
	"Type" character varying(100)
);

ALTER TABLE "DataType" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "DataType" ADD CONSTRAINT "DataType_pkey" PRIMARY KEY (id);

CREATE TABLE "EnumerationType" (
	id UUID NOT NULL
);

ALTER TABLE "EnumerationType" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "EnumerationType" ADD CONSTRAINT "EnumerationType_pkey" PRIMARY KEY (id);


CREATE TABLE "EnumerationValue" (
	id UUID NOT NULL,
	"Key" character varying(10485760),
	"Value" character varying(10485760),
	"Enumeration Type" UUID NOT NULL
);

ALTER TABLE "EnumerationValue" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "EnumerationValue" ADD CONSTRAINT "EnumerationValue_pkey" PRIMARY KEY (id);


CREATE TABLE "PrimitiveType" (
	id UUID NOT NULL,
	"Units" character varying(100)
);

ALTER TABLE "PrimitiveType" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "PrimitiveType" ADD CONSTRAINT "PrimitiveType_pkey" PRIMARY KEY (id);


CREATE TABLE "ReferenceType" (
	id UUID NOT NULL,
	"Referenced Class" UUID NOT NULL
);

ALTER TABLE "ReferenceType" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "ReferenceType" ADD CONSTRAINT "ReferenceType_pkey" PRIMARY KEY (id);


CREATE TABLE "Database" (
	id UUID NOT NULL,
	"Dialect" character varying (100)
);

ALTER TABLE "Database" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Database" ADD CONSTRAINT "Database_pkey" PRIMARY KEY (id);


CREATE TABLE "DataSet" (
	id UUID NOT NULL
);

ALTER TABLE "DataSet" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "DataSet" ADD CONSTRAINT "DataSet_pkey" PRIMARY KEY (id);


CREATE TABLE "DataStandard" (
	id UUID NOT NULL
);

ALTER TABLE "DataStandard" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "DataStandard" ADD CONSTRAINT "DataStandard_pkey" PRIMARY KEY (id);


CREATE TABLE "Form" (
	id UUID NOT NULL
);

ALTER TABLE "Form" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Form" ADD CONSTRAINT "Form_pkey" PRIMARY KEY (id);


CREATE TABLE "Message" (
	id UUID NOT NULL
);

ALTER TABLE "Message" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Message" ADD CONSTRAINT "Message_pkey" PRIMARY KEY (id);


CREATE TABLE "Report" (
	id UUID NOT NULL
);

ALTER TABLE "Report" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Report" ADD CONSTRAINT "Report_pkey" PRIMARY KEY (id);


CREATE TABLE "Workflow" (
	id UUID NOT NULL
);

ALTER TABLE "Workflow" OWNER TO metadatacatalogue;
ALTER TABLE ONLY "Workflow" ADD CONSTRAINT "Workflow_pkey" PRIMARY KEY (id);

