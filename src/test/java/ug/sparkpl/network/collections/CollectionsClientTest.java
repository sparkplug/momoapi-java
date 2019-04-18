package ug.sparkpl.network.collections;

public class CollectionsClientTest {

    /*


    public void testGetListUserByGroup() throws Exception {
        final MockWebServer server = mockWebServer();

        server.enqueue(new MockResponse()
                .setBody(payloadFromResource("/admin-list-user-by-group.json"))
                .setResponseCode(200));
        try (final BitbucketApi baseApi = api(server.getUrl("/"))) {

            final UserPage up = baseApi.adminApi().listUsersByGroup(localContext, null, 0, 2);
            assertThat(up).isNotNull();
            assertThat(up.errors()).isEmpty();
            assertThat(up.size() == 2).isTrue();
            assertThat(up.values().get(0).slug().equals("bob123")).isTrue();

            final Map<String, ?> queryParams = ImmutableMap.of("context", localContext, limitKeyword, 2, startKeyword, 0);
            assertSent(server, getMethod, restApiPath + BitbucketApiMetadata.API_VERSION
                    + "/admin/groups/more-members", queryParams);
        } finally {
            server.shutdown();
        }
    }


    public void testGetListUserByGroupOnError() throws Exception {
        final MockWebServer server = mockWebServer();

        server.enqueue(new MockResponse()
                .setBody(payloadFromResource("/admin-list-user-by-group-error.json"))
                .setResponseCode(401));
        try (final BitbucketApi baseApi = api(server.getUrl("/"))) {

            final UserPage up = baseApi.adminApi().listUsersByGroup(localContext, null, 0, 2);
            assertThat(up).isNotNull();
            assertThat(up.errors()).isNotEmpty();

            final Map<String, ?> queryParams = ImmutableMap.of("context", localContext, limitKeyword, 2, startKeyword, 0);
            assertSent(server, getMethod, restApiPath + BitbucketApiMetadata.API_VERSION
                    + "/admin/groups/more-members", queryParams);
        } finally {
            server.shutdown();
        }
    }


    public void testAddBuildStatus() throws Exception {
        final MockWebServer server = mockWebServer();

        server.enqueue(new MockResponse().setBody(payloadFromResource("/build-status-post.json")).setResponseCode(204));
        try (final BitbucketApi baseApi = api(server.getUrl("/"))) {

            final CreateBuildStatus cbs = CreateBuildStatus.create(CreateBuildStatus.STATE.SUCCESSFUL,
                    "REPO-MASTER",
                    "REPO-MASTER-42",
                    "https://bamboo.example.com/browse/REPO-MASTER-42",
                    "Changes by John Doe");
            final RequestStatus success = baseApi.buildStatusApi().add(commitHash, cbs);
            assertThat(success).isNotNull();
            assertThat(success.value()).isTrue();
            assertThat(success.errors()).isEmpty();

            assertSent(server, "POST", restBuildStatusPath + BitbucketApiMetadata.API_VERSION
                    + commitPath);
        } finally {
            server.shutdown();
        }
    }


    public void testGetCommit() throws Exception {
        final MockWebServer server = mockWebServer();

        server.enqueue(new MockResponse().setBody(payloadFromResource("/commit.json")).setResponseCode(200));
        try (final BitbucketApi baseApi = api(server.getUrl("/"))) {

            final Commit commit = baseApi.commitsApi().get(projectKey, repoKey, commitHash, null);
            assertThat(commit).isNotNull();
            assertThat(commit.errors().isEmpty()).isTrue();
            assertThat(commit.id().equalsIgnoreCase(commitHash)).isTrue();

            assertSent(server, getMethod, restBasePath + BitbucketApiMetadata.API_VERSION
                    + "/projects/" + projectKey + "/repos/" + repoKey + "/commits/" + commitHash);
        } finally {
            server.shutdown();
        }
    }


    public void testCreateProject() throws Exception {
        final MockWebServer server = mockWebServer();

        server.enqueue(new MockResponse()
                .setBody(payloadFromResource("/project.json"))
                .setResponseCode(201));
        try (final BitbucketApi baseApi = api(server.getUrl("/"))) {

            final String projectKey = "HELLO";
            final CreateProject createProject = CreateProject.create(projectKey, null, null, null);
            final Project project = baseApi.projectApi().create(createProject);

            assertThat(project).isNotNull();
            assertThat(project.errors()).isEmpty();
            assertThat(project.key()).isEqualToIgnoringCase(projectKey);
            assertThat(project.name()).isEqualToIgnoringCase(projectKey);
            assertThat(project.links()).isNotNull();
            assertSent(server, "POST", restBasePath + BitbucketApiMetadata.API_VERSION + localPath);
        } finally {
            server.shutdown();
        }
    }


    public void testCreateProjectWithIllegalName() throws Exception {
        final MockWebServer server = mockWebServer();

        server.enqueue(new MockResponse()
                .setBody(payloadFromResource("/project-create-fail.json"))
                .setResponseCode(400));
        try (final BitbucketApi baseApi = api(server.getUrl("/"))) {

            final String projectKey = "9999";
            final CreateProject createProject = CreateProject.create(projectKey, null, null, null);
            final Project project = baseApi.projectApi().create(createProject);

            assertThat(project).isNotNull();
            assertThat(project.errors()).isNotEmpty();
            assertSent(server, "POST", restBasePath + BitbucketApiMetadata.API_VERSION + localPath);
        } finally {
            server.shutdown();
        }
    }


    public void testCreateBranch() throws Exception {
        final MockWebServer server = mockWebServer();

        server.enqueue(new MockResponse().setBody(payloadFromResource("/branch.json")).setResponseCode(200));
        try (final BitbucketApi baseApi = api(server.getUrl("/"))) {

            final String branchName = "dev-branch";
            final String commitHash = "8d351a10fb428c0c1239530256e21cf24f136e73";

            final CreateBranch createBranch = CreateBranch.create(branchName, commitHash, null);
            final Branch branch = baseApi.branchApi().create(projectKey, repoKey, createBranch);
            assertThat(branch).isNotNull();
            assertThat(branch.errors().isEmpty()).isTrue();
            assertThat(branch.id().endsWith(branchName)).isTrue();
            assertThat(commitHash.equalsIgnoreCase(branch.latestChangeset())).isTrue();
            assertSent(server, "POST", localRestPath + BitbucketApiMetadata.API_VERSION
                    + localProjectsPath + projectKey + localReposPath + repoKey + localBranchesPath);
        } finally {
            server.shutdown();
        }
    }



     */


}
